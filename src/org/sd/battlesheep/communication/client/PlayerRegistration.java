package org.sd.battlesheep.communication.client;



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import org.sd.battlesheep.Resources;
import org.sd.battlesheep.communication.server.LobbyJoinRemoteInterface;
import org.sd.battlesheep.model.server.NetPlayer;



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