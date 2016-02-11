package org.sd.battlesheep.communication.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import org.sd.battlesheep.model.field.Move;

public interface PlayersConnectedInterface {
	
	public void onTurnOwnerCanMove();
	
	public void notifyNotConnectedUser(String username);
	
	public void updateMove(Move newMove);
	
	public void pushMoveUpdate(String userName, Move newMove) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException;
}
