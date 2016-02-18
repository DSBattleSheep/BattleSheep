/*
 * Battlesheep is a funny remake of the famous Battleship game, developed
 * as a distributed system.
 * 
 * Copyright (C) 2016 - Giulio Biagini, Michele Corazza, Gianluca Iselli
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sd.battlesheep;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.client.PlayersConnectedInterface;
import org.sd.battlesheep.communication.client.PlayerClient;
import org.sd.battlesheep.communication.client.PlayerRegistration;
import org.sd.battlesheep.communication.client.PlayerServer;
import org.sd.battlesheep.model.KickedOutPlayerException;
import org.sd.battlesheep.model.MaxPortRetryException;
import org.sd.battlesheep.model.ModelConst;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.field.Move;
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.model.player.APlayer;
import org.sd.battlesheep.model.player.Me;
import org.sd.battlesheep.model.player.Opponent;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.game.GameFrame;
import org.sd.battlesheep.view.game.GameFrameObserver;
import org.sd.battlesheep.view.registration.RegistrationFrame;
import org.sd.battlesheep.view.registration.RegistrationFrameObserver;

/**
 * Classe principale per il client (gioco).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Battlesheep implements RegistrationFrameObserver, GameFrameObserver, PlayersConnectedInterface {
	private static Battlesheep instance;

	private RegistrationFrame registrationFrame;

	private GameFrame gameFrame;

	private PlayerServer playerServer;

	private Me me;

	private Map<String, APlayer> playerMap;

	private List<APlayer> orderList;

	private List<String> crashedOpponents;

	private ReentrantLock pLock;

	private Condition myTurnEnded;

	private Move lastMove;

	public static Battlesheep getInstance() {
		return Battlesheep.instance;
	}

	public Battlesheep() {
		Battlesheep.instance = this;

		orderList = new ArrayList<APlayer>();
		playerMap = new HashMap<String, APlayer>();
		crashedOpponents = new ArrayList<String>();

		pLock = new ReentrantLock();
		myTurnEnded = pLock.newCondition();

		registrationFrame = new RegistrationFrame(
				ModelConst.FIELD_ROWS, 
				ModelConst.FIELD_COLS,
				ModelConst.SHEEPS_NUMBER, 
				this);
	}

	private class DisposeRegistrationFrameAndCreateGameFrame implements Runnable {

		private String username;

		private boolean[][] sheepsPosition;
		
		private ArrayList<String> opponentList;

		private DisposeRegistrationFrameAndCreateGameFrame(String username, boolean[][] sheepsPosition, ArrayList<String> opponentList) {
			this.username = username;
			this.sheepsPosition = sheepsPosition;
			this.opponentList = opponentList;
		}

		@Override
		public void run() {
			registrationFrame.dispose();
			gameFrame = new GameFrame(
					username, 
					sheepsPosition,
					opponentList.toArray(new String[opponentList.size()]),
					Battlesheep.getInstance());
		}
	}

	private class GameFrameSetTurnRunnable implements Runnable {

		String username;

		private GameFrameSetTurnRunnable(String username) {
			this.username = username;
		}

		@Override
		public void run() {
			gameFrame.setTurn(username);
		}
	}

	private class GameFrameSetAttackResultRunnable implements Runnable {

		String attacker;

		String defender;

		int x;

		int y;

		boolean hit;

		private GameFrameSetAttackResultRunnable(String usernameAttacker, Move move) {
			this.attacker = usernameAttacker;
			this.defender = move.getTarget();
			this.x = move.getX();
			this.y = move.getY();
			this.hit = move.isHit();
		}

		@Override
		public void run() {
			gameFrame.attackResult(attacker, defender, x, y, hit);
		}
	}

	@Override
	public void onRegistrationFrameExitClick() {
		registrationFrame.dispose();
		if (playerServer != null)
			unbindAndClose(0);
	}

	private void removeFromActivePlayers(final String username, final boolean crashed) {

		System.out.println("removeFromActivePlayers(" + username + ")");

		if (crashed)
			crashedOpponents.add(username);
		orderList.remove(playerMap.get(username));
		playerMap.remove(username);

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					if (crashed)
						gameFrame.playerCrashed(username);
					else
						gameFrame.playerLost(username);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void hitPlayer(Opponent target, int x, int y, boolean hit) {
		target.setHit(x, y, hit);
		if (target.lost()) {
			System.out.println(target.getUsername() + " has lost!");
			removeFromActivePlayers(target.getUsername(), false);
		}
	}

	private void unlockRegistration() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				registrationFrame.unlock();
			}
		});
	}

	@Override
	public void onRegistrationFrameRegistrationClick(final String lobbyAddress, final String myUsername,
			final boolean[][] sheepsPosition) {

		new Thread(new Runnable() {
			@Override
			public void run() {

				Map<String, NetPlayer> players = null;

				System.out.println("Console di: " + myUsername);

				try {
					me = new Me(myUsername, sheepsPosition);
					if (playerServer == null)
						playerServer = new PlayerServer(Battlesheep.getInstance());
					playerServer.setMe(me);
					players = PlayerRegistration.Join(lobbyAddress, myUsername, playerServer.getPort());

				} catch (UsernameAlreadyTakenException e) {
					MessageFactory.errorDialog(registrationFrame, e.getMessage());
					unlockRegistration();
					return;
				} catch (ConnectException | ConnectIOException e) {
					MessageFactory.errorDialog(registrationFrame, "Can't reach the lobby server!");
					unlockRegistration();
					return;
				} catch (UnmarshalException e) {
					MessageFactory.errorDialog(registrationFrame, "Lobby server seems to be crashed! Please restart it and retry..");
					unlockRegistration();
					return;
				} catch (MaxPortRetryException e) {
					MessageFactory.errorDialog(registrationFrame, e.getMessage());
					unbindAndClose(1);
				} catch (NotBoundException | ServerNotActiveException | MalformedURLException | AccessException  e) {
					MessageFactory.errorDialog(registrationFrame, e.getMessage());
					e.printStackTrace();
					return;
				} catch (RemoteException e) {
					e.printStackTrace();
					return;
				}

				// Abbiamo joinato e la lobby ci ha restituito tutti i player

				List<String> turnList = PlayerClient.getOrder(myUsername, playerServer.getValueRandom(), players);

				System.out.println("registrato!");

				ArrayList<String> opponentList = new ArrayList<String>();

				orderList.clear();
				playerMap.clear();

				for (String username : turnList) {
					if (username == myUsername) {
						orderList.add(me);
						playerMap.put(myUsername, me);
					} else {
						NetPlayer currPlayer = players.get(username);
						Opponent opponent = new Opponent(
								currPlayer, 
								ModelConst.FIELD_ROWS, 
								ModelConst.FIELD_COLS,
								ModelConst.SHEEPS_NUMBER);
						orderList.add(opponent);
						playerMap.put(opponent.getUsername(), opponent);
						opponentList.add(username);
					}
				}

				if (opponentList.size() == 0) {
					MessageFactory.informationDialog(null, "Game has started without opponents..");
					unbindAndClose(0);
				}
					
				try {
					SwingUtilities.invokeAndWait(new DisposeRegistrationFrameAndCreateGameFrame(myUsername, sheepsPosition, opponentList));
				} catch (InvocationTargetException | InterruptedException e1) {
					e1.printStackTrace();
				}

				System.out.println("gameLoop()!!!");
				gameLoop();
			}
		}).start();
	}

	private void gameLoop() {
		boolean ended = false;
		boolean kickedOut = false;
		boolean removedTurnOwner = false;
		int currPlayerIndex = 0;
		APlayer turnOwner;

		lastMove = new Move("", "", 0, 0, false, null, 0);

		while (!ended && !kickedOut) {
			// se è il mio turno assegno il numero di opponent (ovvero mi
			// sblocco)
			turnOwner = orderList.get(currPlayerIndex);
			crashedOpponents.clear();

			if (turnOwner instanceof Me) {
				playerServer.setExpectedPlayers(orderList, lastMove);

				pLock.lock();
				try {
					myTurnEnded.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					pLock.unlock();
				}

			} else {
				try {
					SwingUtilities.invokeLater(new GameFrameSetTurnRunnable(turnOwner.getUsername()));
					lastMove = PlayerClient.connectToPlayer((Opponent) turnOwner, me.getUsername(), lastMove);
					SwingUtilities.invokeLater(new GameFrameSetAttackResultRunnable(turnOwner.getUsername(), lastMove));

					if (lastMove.getTarget().equals(me.getUsername()))
						me.hit(lastMove.getX(), lastMove.getY());
					else
						hitPlayer(
								(Opponent) playerMap.get(lastMove.getTarget()), 
								lastMove.getX(), 
								lastMove.getY(),
								lastMove.isHit());

					for (String removedPlayer : lastMove.getCrashedOpponents())
						removeFromActivePlayers(removedPlayer, true);

				} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
					// il tizio col turno è morto, ricomincio

					System.out.println(turnOwner.getUsername() + " is crashed!");

					removedTurnOwner = true;
				} catch (KickedOutPlayerException e) {
					kickedOut = true;
				}
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!removedTurnOwner)
				currPlayerIndex = (orderList.indexOf(turnOwner) + 1) % orderList.size();
			else {
				currPlayerIndex = orderList.indexOf(turnOwner) % (orderList.size() - 1);
				removeFromActivePlayers(turnOwner.getUsername(), true);
				removedTurnOwner = false;
			}

			if (orderList.size() == 1 || me.lost())
				ended = true;
		}
		if (kickedOut) {
			System.out.println("I've been kicked out!! <.<");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gameFrame.matchResult(orderList.size(), true);
				}
			});
		} else if (!me.lost()) {
			System.out.println("WIIIIIINNER!! YO!");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gameFrame.matchResult(1, false);
				}
			});
		} else {
			System.out.println("You loooose! :'(");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gameFrame.matchResult(orderList.size(), false);
				}
			});
		}
	}

	@Override
	public void onTurnOwnerCanMove() {
		if (orderList.size() > 1)
			SwingUtilities.invokeLater(new GameFrameSetTurnRunnable(me.getUsername()));
		else {
			pLock.lock();
			myTurnEnded.signal();
			pLock.unlock();
		}
	}

	@Override
	public void notifyNotConnectedUser(String username) {
		System.out.println(username + " did not connect!");
		removeFromActivePlayers(username, true);
	}

	@Override
	public void onGameFrameAttack(final String username, final int x, final int y) {
		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					Opponent target = (Opponent) playerMap.get(username);
					if (target == null)
						throw new NullPointerException(username + " does not exist in playerMap");
					boolean hit = PlayerClient.attackPlayer(target, x, y);
					hitPlayer(target, x, y, hit);
					lastMove = new Move(me.getUsername(), username, x, y, hit, crashedOpponents, lastMove.getMoveIndex() + 1);
					playerServer.setMove(lastMove);

					SwingUtilities.invokeLater(new GameFrameSetAttackResultRunnable(me.getUsername(), lastMove));

					pLock.lock();
					myTurnEnded.signal();
					pLock.unlock();

				} catch (NullPointerException | MalformedURLException | RemoteException | NotBoundException
						| ServerNotActiveException e) {
					System.out.println(e.getMessage());
					removeFromActivePlayers(username, true);
					onTurnOwnerCanMove();
				}
			}
		}).start();
	}

	@Override
	public void updateMove(Move newMove) {
		lastMove = newMove;
		if (!newMove.getTarget().equals(me.getUsername())) {
			Opponent target = (Opponent) playerMap.get(newMove.getTarget());
			hitPlayer(target, newMove.getX(), newMove.getY(), newMove.isHit());
		} else {
			me.hit(newMove.getX(), newMove.getY());
		}
		
		SwingUtilities.invokeLater(new GameFrameSetAttackResultRunnable(lastMove.getAttacker(), lastMove));
	}

	@Override
	public void pushMoveUpdate(String username, Move newMove)
			throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException {
		Opponent target = (Opponent) playerMap.get(username);
		PlayerClient.pushMoveUpdate(target, newMove);
	}

	public void unbindAndClose(int status) {
		playerServer.close();
		System.exit(status);
	}

	@Override
	public void onGameFrameExitClick() {
		unbindAndClose(0);
	}
}