package lobby;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lobby.view.LobbyFrame;


public class LobbyMain {

	private LobbyFrame lobbyFrame;
	
	private LobbyMain() {
		lobbyFrame = new LobbyFrame("192.168.1.100", 25099);
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
