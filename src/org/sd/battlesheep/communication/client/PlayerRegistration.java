package org.sd.battlesheep.communication.client;



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinRemoteInterface;
import org.sd.battlesheep.model.lobby.NetPlayer;



public class PlayerRegistration
{
	public static Map<String, NetPlayer> Join(String username, int port) 
			throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException {
		
		Map<String, NetPlayer> playerMap;
		LobbyJoinRemoteInterface serverInterface = (LobbyJoinRemoteInterface) Naming
				.lookup("rmi://127.0.0.1:" + CommunicationConst.LOBBY_PORT + "/" + CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);
		playerMap = serverInterface.JoinLobby(username, port);
		
		playerMap.remove(username);
		
		return playerMap;	
	}
}