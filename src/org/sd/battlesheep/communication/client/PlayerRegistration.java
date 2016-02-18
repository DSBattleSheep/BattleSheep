package org.sd.battlesheep.communication.client;



import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinRemoteInterface;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;



public class PlayerRegistration
{
	public static Map<String, NetPlayer> Join(String lobbyAddress, String username, int playerPort) 
			throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException, UnmarshalException, UsernameAlreadyTakenException, AccessException, ConnectIOException {
		
		Map<String, NetPlayer> playerMap;
		
		Registry registry = LocateRegistry.getRegistry(lobbyAddress, CommunicationConst.LOBBY_PORT);
		LobbyJoinRemoteInterface serverInterface = (LobbyJoinRemoteInterface) registry.lookup(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);	
		
		playerMap = serverInterface.joinLobby(username, playerPort);
		
		playerMap.remove(username);
		
		return playerMap;	
	}
}