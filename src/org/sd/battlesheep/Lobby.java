package org.sd.battlesheep;



import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyServerRMI;
import org.sd.battlesheep.view.lobby.LobbyFrame;



/**
 * Classe principale per il server (lobby).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Lobby 
{
	private LobbyFrame lobbyFrame;
	
	private LobbyServerRMI lobbyServer;
	
	
	
	public Lobby() {
		try {
			String currHost = Utils.getLocalAddress().getHostAddress();
			if (currHost == null)
				currHost = "127.0.0.1";
			
			lobbyServer = new LobbyServerRMI(CommunicationConst.LOBBY_PORT);
			lobbyFrame = new LobbyFrame(currHost, CommunicationConst.LOBBY_PORT);
			
			lobbyFrame.setOnStartObserver(lobbyServer);
			lobbyServer.setLobbyJoinFrameObserver(lobbyFrame);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}