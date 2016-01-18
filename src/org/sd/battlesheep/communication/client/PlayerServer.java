package org.sd.battlesheep.communication.client;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.model.MaxPortRetryException;
import org.sd.battlesheep.model.field.Move;
import org.sd.battlesheep.model.player.Me;


@SuppressWarnings("serial")
public class PlayerServer extends UnicastRemoteObject implements OrderInterface, MyTurnInterface  {
	
	
	private int myValueRandom;
	
	private Me me;
	private ReentrantLock pLock;
	private Condition moveSelectedCondition, itsMyTUrn;
	private boolean myTurnStarted;
	
	private int playerNum=-1;
	private int port;
	private int countConnected;
	
	public PlayerServer() throws RemoteException, MaxPortRetryException {
		super();
		
		int retry = 0;
		countConnected=0;
		myTurnStarted=false;
		pLock=new ReentrantLock();
		moveSelectedCondition=pLock.newCondition();
		itsMyTUrn=pLock.newCondition();
		int port = CommunicationConst.DEFAULT_PORT;
		boolean notBound = true;
		Random randomGenerator = new Random();
		
		while (retry < CommunicationConst.MAX_RETRY && notBound) {
			try {
				Registry registry = LocateRegistry.createRegistry(port);
				registry.bind(CommunicationConst.GAME_SERVICE_NAME, this);
				notBound = false;
				this.port = port;
			} catch (AlreadyBoundException | ExportException e) {
				retry++;
				port = 20000 + randomGenerator.nextInt(45535);
				if (retry == CommunicationConst.MAX_RETRY)
					throw new MaxPortRetryException("MaxPortRetryException");
			}
		}
		
		System.out.println("bindato su porta: " + port);
		
		myValueRandom = randomGenerator.nextInt();
	}

	
	public void setMe(Me me) {
		this.me = me;
	}

	public void setPlayerNum(int playerNum) {
		pLock.lock();
		this.playerNum=playerNum;
		this.myTurnStarted=true;
		itsMyTUrn.signalAll();
		pLock.unlock();
	}
	
	/**
	 * funzione che si occupa di restituire la porta per la comunicazione con
	 * gli altri giocatori
	 * 
	 * @return la porta per la comunicazione con gli altri giocatori
	 */
	public int getPort() {
		return port;
	}
	

	@Override
	public int getValueRandom() {
		return myValueRandom;
	}
	
	@Override
	public Move connectCurrentPlayer(String username){
		pLock.lock();
		if(this.myTurnStarted)
			try{
				itsMyTUrn.wait();
			}
			catch (InterruptedException e) {
				System.out.println("Qui siamo messi malino");
				e.printStackTrace();
			}
		countConnected++;
		if(countConnected==playerNum)
			//segnala che posso scegliere la mossa, SBLOCCATIIII
		pLock.unlock();
		//FIXME
		return null;
	}
	
}
