package org.sd.battlesheep.communication.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import org.sd.battlesheep.model.field.Move;

public interface MyTurnInterface extends Remote {
	
	public Move connectCurrentPlayer(String user) throws RemoteException, ServerNotActiveException;
	
}