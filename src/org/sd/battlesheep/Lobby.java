package org.sd.battlesheep;



import org.sd.battlesheep.communication.lobby.LobbyServerRMI;
import org.sd.battlesheep.view.lobby.LobbyFrame;



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