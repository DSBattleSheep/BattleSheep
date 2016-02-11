package org.sd.battlesheep.communication.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import org.sd.battlesheep.model.field.Move;

public interface PushMoveInterface extends Remote {

	public void pushMoveUpdate(Move newMove) throws RemoteException, ServerNotActiveException;
	
}