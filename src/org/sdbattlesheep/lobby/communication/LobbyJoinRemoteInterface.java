package org.sdbattlesheep.lobby.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import org.sdbattlesheep.lobby.model.NetPlayer;

public interface LobbyJoinRemoteInterface extends Remote {
	
	public List<NetPlayer> JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException;
}