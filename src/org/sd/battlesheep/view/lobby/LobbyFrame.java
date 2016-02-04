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
import javax.swing.table.DefaultTableModel;

import org.sd.battlesheep.view.AFrame;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LobbyFrame extends AFrame
{
	private static final int WIDTH = 400;
	
	private static final int HEIGHT = 400;
	
	
	
	private WaitingPanel waitingPanel;
	
	private TablePanel tablePanel;
	
	
	
	private LobbyFrameObserver observer;
	
	
	
	public LobbyFrame(String host, int port, LobbyFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* waiting panel */
		
		waitingPanel = new WaitingPanel(host, port);
		waitingPanel.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		
		/* table panel */
		
		tablePanel = new TablePanel(host, port);
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
	
	
	
	private void actionExit() {
		if (observer != null)
			observer.onLobbyFrameExitClick();
	}
	
	private void actionStart() {
		if (observer != null)
			observer.onLobbyFrameStartClick();
	}
	
	
	
	public void addClient(String username, String host, int port) {
		DefaultTableModel clientsTableModel = tablePanel.getClientsTableModel();
		if (clientsTableModel.getRowCount() == 0) {
			remove(waitingPanel);
			add(tablePanel, BorderLayout.CENTER);
		}
		clientsTableModel.addRow(new String[]{username, host, port + ""});
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	
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
}