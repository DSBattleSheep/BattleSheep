package org.sdbattlesheep.lobby;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.sdbattlesheep.battlesheep.model.ModelResources;
import org.sdbattlesheep.lobby.communication.LobbyServerRMI;
import org.sdbattlesheep.lobby.view.LobbyFrame;

import utils.Utils;


public class Lobby 
{

	private LobbyFrame lobbyFrame;
	
	private LobbyServerRMI lobbyServer;
	
	
	private Lobby() {
		
		
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
		
		new Lobby();
	}

}
