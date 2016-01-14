package org.sdbattlesheep.lobby;



import org.sdbattlesheep.Resources;
import org.sdbattlesheep.lobby.communication.LobbyServerRMI;
import org.sdbattlesheep.lobby.view.LobbyFrame;

import utils.Utils;



public class Lobby 
{
	private LobbyFrame lobbyFrame;
	
	private LobbyServerRMI lobbyServer;
	
	
	
	public Lobby() {
		try {
			String currHost = Utils.getLocalAddress().getHostAddress();
		
			lobbyServer = new LobbyServerRMI(Resources.PORT_LOBBY);
			lobbyFrame = new LobbyFrame(currHost, Resources.PORT_LOBBY);
			
			lobbyFrame.setOnStartObserver(lobbyServer);
			lobbyServer.setLobbyJoinFrameObserver(lobbyFrame);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}