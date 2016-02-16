package org.sd.battlesheep.communication.client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.model.KickedOutPlayerException;
import org.sd.battlesheep.model.MaxPortRetryException;
import org.sd.battlesheep.model.field.Move;
import org.sd.battlesheep.model.player.APlayer;
import org.sd.battlesheep.model.player.Me;
import org.sd.battlesheep.model.player.Opponent;


public class PlayerServer extends UnicastRemoteObject implements OrderInterface, TurnOwnerInterface, AttackInterface, PushMoveInterface {

	private static final long serialVersionUID = 5096751122974952805L;
	
	private static final int PLAYERS_CONNECTION_MAX_TIMEOUT = 500; // millis

	
	private PlayersConnectedInterface observer;

	private int boundPort;

	private int myValueRandom;
	
	private Registry registry;

	private Me me;

	private ReentrantLock pLock;

	private Condition moveSelectedCondition;

	private Condition itsMyTurn;

	private Condition allClientsConnected;

	private int turnIndex;

	private int playerCount;

	private boolean myTurnStarted;

	private boolean expiredDeadline;

	private boolean allPlayersAlreadyConnected;

	private List<String> expectedPlayers;

	private Move currentMove;

	private Move lastMove;

	private class PlayersConnectionTurnTimeOut implements Runnable {

		int currentTurnIndex;

		private PlayersConnectionTurnTimeOut(int currentTurnIndex) {
			this.currentTurnIndex = currentTurnIndex;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(PLAYERS_CONNECTION_MAX_TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pLock.lock();
			if (currentTurnIndex != turnIndex) {
				pLock.unlock();
				return;
			}
			expiredDeadline = true;
			if (allPlayersAlreadyConnected)
				pLock.unlock();
			else {
				pLock.unlock();
				for (String username : expectedPlayers)
					observer.notifyNotConnectedUser(username);
				pLock.lock();
				allClientsConnected.signalAll();
				pLock.unlock();
			}
		}
	}

	public PlayerServer(PlayersConnectedInterface observer) throws RemoteException, MaxPortRetryException {
		super();

		int retry = 0;
		int port = CommunicationConst.DEFAULT_PORT;
		boolean notBound = true;
		Random randomGenerator = new Random();

		while (retry < CommunicationConst.MAX_RETRY && notBound) {
			try {
				registry = LocateRegistry.createRegistry(port);
				registry.bind(CommunicationConst.GAME_SERVICE_NAME, this);
				notBound = false;
				this.boundPort = port;
			} catch (AlreadyBoundException | ExportException e) {
				retry++;
				port = 20000 + randomGenerator.nextInt(45535);
				if (retry == CommunicationConst.MAX_RETRY)
					throw new MaxPortRetryException("MaxPortRetryException");
			}
		}

		System.out.println("bindato su porta: " + port);

		this.observer = observer;
		turnIndex = 0;
		myTurnStarted = false;
		expiredDeadline = false;
		allPlayersAlreadyConnected = false;
		pLock = new ReentrantLock();
		itsMyTurn = pLock.newCondition();
		moveSelectedCondition = pLock.newCondition();
		allClientsConnected = pLock.newCondition();
		expectedPlayers = new ArrayList<String>();
		myValueRandom = randomGenerator.nextInt();
	}
	
	public void close() {
		try {
			registry.unbind(CommunicationConst.GAME_SERVICE_NAME);
			UnicastRemoteObject.unexportObject(this, true);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void setMe(Me me) {
		this.me = me;
	}

	public void setExpectedPlayers(List<APlayer> allPlayers, Move lastMove) {
		pLock.lock();
		expectedPlayers.clear();
		for (APlayer player : allPlayers) {
			if (player instanceof Opponent)
				expectedPlayers.add(player.getUsername());
		}
		playerCount = 0;
		turnIndex++;
		expiredDeadline = false;
		allPlayersAlreadyConnected = false;
		myTurnStarted = true;
		this.lastMove = lastMove;
		itsMyTurn.signalAll();

		new Thread(new PlayersConnectionTurnTimeOut(turnIndex)).start();

		pLock.unlock();
	}

	public void setMove(Move move) {
		pLock.lock();
		currentMove = move;
		myTurnStarted = false;
		moveSelectedCondition.signalAll();
		pLock.unlock();
	}

	/**
	 * funzione che si occupa di restituire la porta per la comunicazione con
	 * gli altri giocatori
	 * 
	 * @return la porta per la comunicazione con gli altri giocatori
	 */
	public int getPort() {
		return boundPort;
	}

	@Override
	public int getValueRandom() {
		return myValueRandom;
	}

	@Override
	public Move connectToTurnOwner(String username, Move oldMove) throws KickedOutPlayerException {

		pLock.lock();

		if (!myTurnStarted) {
			try {
				itsMyTurn.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (oldMove.getMoveIndex() > lastMove.getMoveIndex()) {
			lastMove = oldMove;
			observer.updateMove(lastMove);
		}

		System.out.println("connectCurrentPlayer: client " + username + " connected");
		
		try {
			if (!expiredDeadline) {
				expectedPlayers.remove(username);
				playerCount++;
				if (expectedPlayers.isEmpty()) {
					// segnala che posso scegliere la mossa, SBLOCCATIIII
					allPlayersAlreadyConnected = true;
					allClientsConnected.signalAll();
				} else {
					try {
						allClientsConnected.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				pLock.unlock();
				throw new KickedOutPlayerException("Sorry! You are in late."); // FIXME: throw new TooLateException!
			}
		} catch (NullPointerException e) {
			pLock.unlock();
			// TODO: così non ci può essere un player che si infila a caso.. Va bene?
			throw new KickedOutPlayerException(username + ": You are not an expected player.");
		}

		if (oldMove.getMoveIndex() < lastMove.getMoveIndex()) {
			try {
				observer.pushMoveUpdate(username, lastMove);
			} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
				observer.notifyNotConnectedUser(username);
				pLock.unlock();
				return null;
			}
		}

		playerCount--;

		if (playerCount == 0)
			observer.onTurnOwnerCanMove();
		
		try {
			moveSelectedCondition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pLock.unlock();
		}

		return currentMove;
	}

	@Override
	public boolean attackPlayer(int x, int y) throws RemoteException, ServerNotActiveException {
		return me.isSheep(x, y);
	}

	@Override
	public void pushMoveUpdate(Move newMove) throws RemoteException, ServerNotActiveException {
		observer.updateMove(newMove);
	}

}
