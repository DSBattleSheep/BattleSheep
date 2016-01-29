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
import java.rmi.ConnectException;
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

import org.sd.battlesheep.communication.client.MoveAvailableInterface;
import org.sd.battlesheep.communication.client.PlayerClient;
import org.sd.battlesheep.communication.client.PlayerRegistration;
import org.sd.battlesheep.communication.client.PlayerServer;
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
public class Battlesheep implements RegistrationFrameObserver, GameFrameObserver, MoveAvailableInterface
{
	private static Battlesheep instance;
	
	
	private RegistrationFrame registrationFrame;
	
	private GameFrame gameFrame;
	
	private PlayerServer playerServer;
	
	
	private Me me;
	
	private Map<String, APlayer> playerMap;
	
	private List<APlayer> orderList;
	
	
	private ReentrantLock pLock;
	
	private Condition myTurnEnded;
	

	public static Battlesheep getInstance() {
		return Battlesheep.instance;
	}
	
	public Battlesheep() {
		Battlesheep.instance = this;
		
		orderList = new ArrayList<APlayer>();
		playerMap = new HashMap<String, APlayer>();
		
		pLock=new ReentrantLock();
		myTurnEnded=pLock.newCondition();
		
		registrationFrame = new RegistrationFrame(
			ModelConst.FIELD_ROWS,
			ModelConst.FIELD_COLS,
			ModelConst.SHEEPS_NUMBER,
			this
		);
	}
	
	private class GameFrameLockRunnable implements Runnable {
		@Override
		public void run() {
			gameFrame.lock();
		}
	}
	
	private class DisposeRegistrationFrameAndCreateGameFrame implements Runnable {

		private String username;
		
		private ArrayList<String> opponentList;
		
		private DisposeRegistrationFrameAndCreateGameFrame(String username, ArrayList<String> opponentList) {
			this.username = username;
			this.opponentList = opponentList;
		}
		
		@Override
		public void run() {
			registrationFrame.dispose();			
			gameFrame = new GameFrame(username, opponentList, ModelConst.FIELD_ROWS, 
					ModelConst.FIELD_COLS, Battlesheep.getInstance());
		}		
	}
	
	private class GameFrameSetTurnRunnable implements Runnable {

		String username;
		
		boolean lock;
		
		private GameFrameSetTurnRunnable(String username, boolean lock) {
			this.username = username;
			this.lock = lock;
		}
		
		@Override
		public void run() {
			gameFrame.setTurn(username);
			if (lock)
				gameFrame.lock();
			else
				gameFrame.unlock();
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
		// registrationFrame.dispose();//
		// TODO -> gestire l'uscita dal programma (client)
		System.exit(0);
	}
	
	@Override
	public void onRegistrationFrameRegistrationClick(final String lobbyAddress, final String myUsername, final boolean[][] sheepsPosition) {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				/*
				 * FIXME se ci sono dei problemi nella join (exception) dobbiamo 
				 * 		 mostrare un messaggio che qualcosa è andato storto,
				 * 		 altrimenti dobbiamo andare nella schermata di gioco!
				 * 		 Ma intanto il la view dovrebbe entrare in una fase di waiting!
				 * 		 Bisogna certamente creare un Observer con almeno 2 metodi: onStart e onError! 
				 */	
				
								
				Map<String, NetPlayer> players = null;
				
				System.out.println("Console di: " + myUsername);
				
				try {
					me = new Me(myUsername, sheepsPosition);
					if (playerServer == null)
						playerServer = new PlayerServer(Battlesheep.getInstance());
					playerServer.setMe(me);
					players = PlayerRegistration.Join(myUsername, playerServer.getPort());
					
				} catch (UsernameAlreadyTakenException e) {
					MessageFactory.errorDialog(null, e.getMessage());
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							registrationFrame.unlock();
						}
					});
					return;
				} catch (MaxPortRetryException e) {
					MessageFactory.errorDialog(null, e.getMessage());
					System.exit(1);
				} catch (NotBoundException | ServerNotActiveException | ConnectException | UnmarshalException | MalformedURLException e) {
					MessageFactory.errorDialog(null, e.getMessage());
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
				
				for(String username : turnList) {
					if(username == myUsername) {
						orderList.add(me);
						playerMap.put(myUsername, me);
					} else {
						NetPlayer currPlayer = players.get(username);
						Opponent opponent = new Opponent(currPlayer, ModelConst.FIELD_ROWS, ModelConst.FIELD_COLS,
								ModelConst.SHEEPS_NUMBER);
						orderList.add(opponent);
						playerMap.put(opponent.getUsername(), opponent);
						opponentList.add(username);
					}
				}
				
				try {
					SwingUtilities.invokeAndWait(new DisposeRegistrationFrameAndCreateGameFrame(myUsername, opponentList));
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
		int currPlayerIndex = 0;
		APlayer turnOwner;
		Move recvdMove;
		Opponent hitTarget;
		
		while(!ended) {
			//se è il mio turno assegno il numero di opponent (ovvero mi sblocco)
			turnOwner = orderList.get(currPlayerIndex);
						
			if(turnOwner instanceof Me) {
				playerServer.setPlayerNum(orderList.size());
				
				pLock.lock();
				try {
					myTurnEnded.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					pLock.unlock();
				}
				
			} else {
				try {
					SwingUtilities.invokeLater(new GameFrameSetTurnRunnable(turnOwner.getUsername(), true));
					recvdMove = PlayerClient.connectToPlayer((Opponent) turnOwner, me.getUsername());
					SwingUtilities.invokeLater(new GameFrameSetAttackResultRunnable(turnOwner.getUsername(), recvdMove));
					if(recvdMove.getTarget().equals(me.getUsername())) {
						me.setHit(recvdMove.getX(), recvdMove.getY());
					} else {
						hitTarget=(Opponent) playerMap.get(recvdMove.getTarget());
						hitTarget.setHit(recvdMove.getX(), recvdMove.getY(), recvdMove.isHit());
					}
				} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//il tizio col turno è morto, ricomincio
					
					orderList.remove(currPlayerIndex);
					playerMap.remove(turnOwner.getUsername());
					
					//TODO avvisare la vista del crash!
					
					continue;
				}
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currPlayerIndex = (currPlayerIndex + 1) % orderList.size();
			//se non è il mio turno, chiedo la mossa al player che ha il turno
			
			if (orderList.size() == 1 || me.lost())
				ended = true;
		}
		if (! me.lost())
			System.out.println("WIIIIIINNER!! YO!");
		else 
			System.out.println("You loooose! :'(");
	}



	@Override
	public void canMove() {
		SwingUtilities.invokeLater(new GameFrameSetTurnRunnable(me.getUsername(), false));
	}
	
	

	@Override
	public void onGameFrameAttack(final String username, final int x, final int y) {
		new Thread(new Runnable() {
			@Override
			public void run() {	
				try {
					SwingUtilities.invokeAndWait(new GameFrameLockRunnable());
				} catch (InvocationTargetException | InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					boolean hit = PlayerClient.attackPlayer((Opponent) playerMap.get(username), x, y);
					Move move = new Move(username, x, y, hit);
					playerServer.setMove(move);

					SwingUtilities.invokeLater(new GameFrameSetAttackResultRunnable(me.getUsername(), move));
					
					pLock.lock();
					myTurnEnded.signal();
					pLock.unlock();
					
				} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	

	@Override
	public void onGameFrameExitClick() {
		//FIXME chiudi i thread di RMI
		System.exit(0);
	}
}