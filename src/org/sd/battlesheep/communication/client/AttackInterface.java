package org.sd.battlesheep.communication.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface AttackInterface extends Remote {

	public boolean attackPlayer(int x, int y) throws RemoteException, ServerNotActiveException;
	
}
