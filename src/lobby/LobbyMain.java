package lobby;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import game.model.ModelResources;
import lobby.server.LobbyServerRMI;
import lobby.view.LobbyFrame;
import utils.Utils;


public class LobbyMain 
{

	private LobbyFrame lobbyFrame;
	
	private LobbyServerRMI lobbyServer;
	
	
	private LobbyMain() {
		
		
		try {
			String currHost = Utils.getLocalAddress().getHostAddress();
		
			lobbyServer = new LobbyServerRMI(ModelResources.PORT_LOBBY);
			lobbyFrame = new LobbyFrame(currHost, ModelResources.PORT_LOBBY);
			
			lobbyFrame.setOnStartObserver(lobbyServer);
			lobbyServer.setLobbyJoinFrameObserver(lobbyFrame);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			
		} catch (InstantiationException ex) {
			
		} catch (IllegalAccessException ex) {
			
		} catch (UnsupportedLookAndFeelException ex) {
			
		}
		
		new LobbyMain();
	}

}
