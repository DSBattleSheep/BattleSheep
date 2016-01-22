package org.sd.battlesheep;



import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinInterface;
import org.sd.battlesheep.communication.lobby.LobbyServerRMI;
import org.sd.battlesheep.communication.lobby.LobbyStartObserver;
import org.sd.battlesheep.view.lobby.LobbyFrame;
import org.sd.battlesheep.view.lobby.LobbyFrame2;
import org.sd.battlesheep.view.lobby.LobbyJoinFrameObserver;



/**
 * Classe principale per il server (lobby).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Lobby implements LobbyJoinInterface, LobbyStartObserver
{
	private LobbyFrame lobbyFrame;
	
	private LobbyServerRMI lobbyServer;
	
	
	private class UpdateGuiThread implements Runnable {

		private LobbyFrame lobbyFrame;
		
		private String username;
		
		private String host;
		
		private int port;
		

		public UpdateGuiThread(LobbyFrame lobbyFrame, String username, String host, int port) {
			this.lobbyFrame = lobbyFrame;
			this.username = username;
			this.host = host;
			this.port = port;
		}
		
		@Override
		public void run() {
			lobbyFrame.addClient(username, host, port);
		}
	}
	
	
	public Lobby() {
		try {
			String currHost = Utils.getLocalAddress().getHostAddress();
			if (currHost == null)
				currHost = "127.0.0.1";
			
			lobbyServer = new LobbyServerRMI(CommunicationConst.LOBBY_PORT, this);
			lobbyFrame = new LobbyFrame(currHost, CommunicationConst.LOBBY_PORT, this);
			
			// new LobbyFrame2();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClientJoin(String username, String host, int port) {
		SwingUtilities.invokeLater(new UpdateGuiThread(lobbyFrame, username, host, port));
	}


	@Override
	public void onLobbyStartClick() {
		lobbyServer.startGame();
	}
}