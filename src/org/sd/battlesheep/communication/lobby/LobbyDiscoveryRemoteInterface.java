package org.sd.battlesheep.communication.lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;


public interface LobbyDiscoveryRemoteInterface extends Remote  {

	public String retrieveLobbyName() throws RemoteException, ServerNotActiveException;
	
}
