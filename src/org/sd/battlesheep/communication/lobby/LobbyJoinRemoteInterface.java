package org.sd.battlesheep.communication.lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import org.sd.battlesheep.model.lobby.NetPlayer;

public interface LobbyJoinRemoteInterface extends Remote {
	
	public List<NetPlayer> JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException;
}