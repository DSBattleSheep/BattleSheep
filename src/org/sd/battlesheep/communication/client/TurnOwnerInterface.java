package org.sd.battlesheep.communication.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import org.sd.battlesheep.model.KickedOutPlayerException;
import org.sd.battlesheep.model.field.Move;

public interface TurnOwnerInterface extends Remote {
	
	public Move connectToTurnOwner(String user, Move oldMove) throws RemoteException, ServerNotActiveException, KickedOutPlayerException;
	
}