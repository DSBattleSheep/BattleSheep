package lobby;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.model.ModelResources;
import lobby.server.LobbyServerRMI;
import lobby.view.LobbyFrame;


public class LobbyMain {

	private LobbyFrame lobbyFrame;
	private LobbyServerRMI lobbyServer;
	
	private LobbyMain() {
		lobbyFrame = new LobbyFrame("192.168.1.100", ModelResources.PORT_LOBBY);
		try{
			lobbyServer=new LobbyServerRMI(ModelResources.PORT_LOBBY, lobbyFrame);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		lobbyFrame.RegisterOnStart(lobbyServer);
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
