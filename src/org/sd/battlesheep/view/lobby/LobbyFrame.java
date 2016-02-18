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



package org.sd.battlesheep.view.lobby;



import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.lobby.observer.ClientsPanelObserver;
import org.sd.battlesheep.view.lobby.observer.LobbyFrameObserver;
import org.sd.battlesheep.view.lobby.panel.ClientsTablePanel;
import org.sd.battlesheep.view.lobby.panel.WaitingPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LobbyFrame extends AFrame implements WaitingPanelObserver, ClientsPanelObserver
{
	private static final int WIDTH = 400;
	
	private static final int HEIGHT = 400;
	
	
	
	private WaitingPanel waitingPanel;
	
	private ClientsTablePanel clientsTablePanel;
	
	
	
	private LobbyFrameObserver observer;
	
	
	
	public LobbyFrame(String host, int port, LobbyFrameObserver observer) {
		super(WIDTH, HEIGHT);
		
		/* model */
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* panels */
		
		waitingPanel = new WaitingPanel(host, port, this);
		
		clientsTablePanel = new ClientsTablePanel(host, port, this);
		
		/* this frame */
		
		addMiddlePanel(waitingPanel);
		setVisible(true);
	}
	
	
	
	@Override
	public void onWaitingPanelExitClick() {
		observer.onLobbyFrameExitClick();
	}
	
	@Override
	public void onClientsPanelExitClick() {
		observer.onLobbyFrameExitClick();
	}
	
	@Override
	public void onClientsPanelStartClick() {
		observer.onLobbyFrameStartClick();
	}
	
	
	
	public void addClient(String username, String host, int port) {
		clientsTablePanel.addClient(username, host, port);
		if (clientsTablePanel.getClientsNumber() == 1)
			replaceMiddlePanel(waitingPanel, clientsTablePanel);
	}
}