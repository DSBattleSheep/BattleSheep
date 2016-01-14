package org.sdbattlesheep.battlesheep.communication;



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import org.sdbattlesheep.Resources;
import org.sdbattlesheep.lobby.communication.LobbyJoinRemoteInterface;
import org.sdbattlesheep.lobby.model.NetPlayer;



public class PlayerRegistration
{
	public static List<NetPlayer> Join(String username, int port) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException {
		List<NetPlayer> playerList;
		LobbyJoinRemoteInterface serverInterface = (LobbyJoinRemoteInterface) Naming
				.lookup("rmi://127.0.0.1:" + Resources.PORT_LOBBY + "/lobby"); // FIXME: static final room name
		playerList = serverInterface.JoinLobby(username, port);
		System.out.println(playerList.get(0).getUsername());
		return playerList;	
	}
}