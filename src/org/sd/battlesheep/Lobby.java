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
		
			lobbyServer = new LobbyServerRMI(CommunicationConst.PORT_LOBBY);
			lobbyFrame = new LobbyFrame(currHost, CommunicationConst.PORT_LOBBY);
			
			lobbyFrame.setOnStartObserver(lobbyServer);
			lobbyServer.setLobbyJoinFrameObserver(lobbyFrame);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}