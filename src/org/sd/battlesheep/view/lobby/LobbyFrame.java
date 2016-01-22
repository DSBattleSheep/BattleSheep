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



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.AFrame;



@SuppressWarnings("serial")
public class LobbyFrame extends AFrame
{
	/*
	 * constants
	 */
	
	private static final int WIDTH = 400;
	
	private static final int HEIGHT = 400;
	
	
	
	/*
	 * graphic
	 */
	
	private WaitingPanel waitingPanel;
	
	private TablePanel tablePanel;
	
	
	
	/*
	 * model
	 */
	
	private LobbyFrameObserver observer;
	
	
	
	/*
	 * constructor
	 */
	
	public LobbyFrame(String host, int port, LobbyFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* waiting panel */
		
		waitingPanel = new WaitingPanel();
		
		/* table panel */
		
		tablePanel = new TablePanel();
		tablePanel.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		tablePanel.getStartButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionStart();
			}
		});
		
		/* this frame */
		
		add(waitingPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	
	/*
	 * actions
	 */
	
	private void actionExit() {
		observer.onLobbyFrameExitClick();
	}
	
	private void actionStart() {
		observer.onLobbyFrameStartClick();
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		waitingPanel.lock();
		tablePanel.lock();
	}
	
	@Override
	public void unlock() {
		waitingPanel.unlock();
		tablePanel.unlock();
	}
	
	
	
	/*
	 * model
	 */
	
	public void addClient(String username, String host, int port) {
		if (tablePanel.getClientsNumber() == 0) {
			remove(waitingPanel);
			add(tablePanel, BorderLayout.CENTER);
		}
		tablePanel.addClient(username, host, port);
		SwingUtilities.updateComponentTreeUI(this);
	}
}