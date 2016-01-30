package org.sd.battlesheep.communication.client;

import java.rmi.AlreadyBoundException;
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

@SuppressWarnings("serial")
public class PlayerServer extends UnicastRemoteObject implements OrderInterface, TurnOwnerInterface, AttackInterface {

	private static final int PLAYERS_CONNECTION_MAX_TIMEOUT = 500; //millis
	
	private PlayersConnectedInterface observer;
	

	private int boundPort;
	
	private int myValueRandom;

	private Me me;
	
	
	private ReentrantLock pLock;
	
	private Condition moveSelectedCondition;
	
	private Condition itsMyTurn;
	
	private int turnIndex;
	
	private boolean myTurnStarted;
	
	private boolean expiredDeadline;
	
	private boolean allPlayersAlreadyConnected;

	private List<String> expectedPlayers;
	
	private Move currentMove;
	
	
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
				observer.onTurnOwnerCanMove();
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
				Registry registry = LocateRegistry.createRegistry(port);
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
		expectedPlayers = new ArrayList<String>();
		myValueRandom = randomGenerator.nextInt();
	}

	public void setMe(Me me) {
		this.me = me;
	}

	public void setExpectedPlayers(List<APlayer> allPlayers) {
		pLock.lock();
		expectedPlayers.clear();
		for (APlayer player : allPlayers) {
			if (player instanceof Opponent)
				expectedPlayers.add(player.getUsername());
		}
		turnIndex++;
		expiredDeadline = false;
		allPlayersAlreadyConnected = false;
		myTurnStarted = true;
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
	public Move connectToTurnOwner(String username) throws KickedOutPlayerException {
		pLock.lock();
		if (! myTurnStarted) {
			try {
				itsMyTurn.await();
			} catch (InterruptedException e) {
				System.out.println("Qui siamo messi malino");
				e.printStackTrace();
			}
		}
		
		System.out.println("connectCurrentPlayer: client " + username + "connected");
		//FIXME gestire lista di user connessi, così rimuoviamo quelli non connessi
		
		try {
			if (!expiredDeadline) {
				expectedPlayers.remove(username);
				if (expectedPlayers.isEmpty()) {
					// segnala che posso scegliere la mossa, SBLOCCATIIII
					allPlayersAlreadyConnected = true;
					observer.onTurnOwnerCanMove();
				}
			} else {
				pLock.unlock();
				throw new KickedOutPlayerException("Sorry! You are in late."); //FIXME: throw new TooLateException! :D				
			}
		} catch (NullPointerException e) {
			pLock.unlock();
			// TODO: così non ci può essere un player che si infila a caso.. Va bene?
			throw new KickedOutPlayerException(username + ": You are not an expected player.");   
		}
		
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

}
