package org.sd.battlesheep.communication.lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;

import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;

public interface LobbyJoinRemoteInterface extends Remote {
	
	public Map<String, NetPlayer> joinLobby(String username, int port) throws RemoteException, ServerNotActiveException, UsernameAlreadyTakenException;
}