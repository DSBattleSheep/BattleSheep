package org.sd.battlesheep.communication.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import org.sd.battlesheep.model.player.Me;


@SuppressWarnings("serial")
public class PlayerServer extends UnicastRemoteObject implements OrderInterface  {


	private Random randomGenerator;
	
	private int myValueRandom;
	
	private Me me;
	
	public PlayerServer() throws RemoteException {
		super();
		
		randomGenerator = new Random();
		myValueRandom = randomGenerator.nextInt();
	}

	public void setMe(Me me) {
		this.me = me;
	}

	@Override
	public int getValueRandom() {
		return myValueRandom;
	}

	
	
	
}
