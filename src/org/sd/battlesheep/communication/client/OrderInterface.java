package org.sd.battlesheep.communication.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface OrderInterface extends Remote {

	public int getValueRandom() throws RemoteException, ServerNotActiveException;
	
}
