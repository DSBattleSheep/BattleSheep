package org.sd.battlesheep;



import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinInterface;
import org.sd.battlesheep.communication.lobby.LobbyServerRMI;
import org.sd.battlesheep.view.lobby.LobbyFrame;
import org.sd.battlesheep.view.lobby.observer.LobbyFrameObserver;



/**
 * Classe principale per il server (lobby).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Lobby implements LobbyJoinInterface, LobbyFrameObserver
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
			System.out.println("è arrivato " + username);
			lobbyFrame.addClient(username, host, port);
		}
	}
	
	
	
	public Lobby() {
		try {
			String currHost = Utils.getLocalAddress().getHostAddress();
			if (currHost == null)
				currHost = "127.0.0.1";
			
			System.out.println("Thread.activeCount()=" + Thread.activeCount());
			
			lobbyServer = new LobbyServerRMI(CommunicationConst.LOBBY_PORT, this);
			
			System.out.println("Thread.activeCount()=" + Thread.activeCount());
			lobbyFrame = new LobbyFrame(currHost, CommunicationConst.LOBBY_PORT, this);
			System.out.println("Thread.activeCount()=" + Thread.activeCount());
		
		} catch (RemoteException | AlreadyBoundException e) {
			if (e instanceof ExportException) {
				System.out.println("Port " + CommunicationConst.LOBBY_PORT + " already bound! Lobby server cannot start..");
				System.exit(1);
			}
			
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onClientJoin(String username, String host, int port) {
		SwingUtilities.invokeLater(new UpdateGuiThread(lobbyFrame, username, host, port));
	}
	
	
	
	@Override
	public void onLobbyFrameExitClick() {
		// lobbyFrame.dispose();
		// TODO -> gestire l'uscita dal programma (lobby)
		System.exit(0);
	}

	@Override
	public void onLobbyFrameStartClick() {
		lobbyFrame.dispose();
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				lobbyServer.startGame();
			}
			
		}).start();
		
	}
}