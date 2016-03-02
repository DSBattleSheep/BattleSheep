/*
 * Battlesheep is a funny remake of the famous Battleship game, developed
 * as a distributed system.
 * 
 * Copyright (C) 2016 - Giulio Biagini, Michele Corazza, Gianluca Iselli
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.sd.battlesheep;



import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinInterface;
import org.sd.battlesheep.communication.lobby.LobbyServerRMI;
import org.sd.battlesheep.view.lobby.LobbyFrame;
import org.sd.battlesheep.view.lobby.LobbyFrameObserver;



/**
 * Classe principale per il server (lobby).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Lobby implements LobbyJoinInterface, LobbyFrameObserver
{
	private LobbyFrame lobbyFrame;
	
	private LobbyServerRMI lobbyServer;
	
	private String currHost;
	
	
	
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
			currHost = Utils.getLocalAddress().getHostAddress();
		} catch (NullPointerException e) {
			currHost = "127.0.0.1";
		}
		
		lobbyFrame = new LobbyFrame(currHost, CommunicationConst.LOBBY_PORT, this);
	}
	
	@Override
	public void onClientJoin(String username, String host, int port) {
		SwingUtilities.invokeLater(new UpdateGuiThread(lobbyFrame, username, host, port));
	}
	
	
	
	@Override
	public void onLobbyFrameExitClick() {
		lobbyFrame.dispose();
		if (lobbyServer != null)
			lobbyServer.close();
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

	@Override
	public void onLobbyFrameLobbyNameEntered(String lobbyName) {
		try {

			lobbyServer = new LobbyServerRMI(lobbyName, CommunicationConst.LOBBY_PORT, this);
		
		} catch (RemoteException | AlreadyBoundException e) {
			if (e instanceof ExportException) {
				System.out.println("Port " + CommunicationConst.LOBBY_PORT + " already bound! Lobby server cannot start..");
				System.exit(1);
			}
			
			e.printStackTrace();
		}
	}
}