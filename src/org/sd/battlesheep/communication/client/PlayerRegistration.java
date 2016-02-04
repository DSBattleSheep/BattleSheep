package org.sd.battlesheep.communication.client;



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinRemoteInterface;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;



public class PlayerRegistration
{
	public static Map<String, NetPlayer> Join(String lobbyAddress, String username, int port) 
			throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException, UnmarshalException, UsernameAlreadyTakenException {
		
		Map<String, NetPlayer> playerMap;
		
		
		LobbyJoinRemoteInterface serverInterface = (LobbyJoinRemoteInterface)LocateRegistry.getRegistry(lobbyAddress, port).lookup(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);	
		
		playerMap = serverInterface.joinLobby(username, port);
		
		playerMap.remove(username);
		
		return playerMap;	
	}
}