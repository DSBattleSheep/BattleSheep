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



package org.sd.battlesheep.view.registration;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.MessageFactory;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class RegistrationFrame extends AFrame
{
	private static final int WIDTH = 500;
	
	private static final int HEIGHT = 550;
	
	
	
	private LobbyAddressPanel lobbyAddressPanel;
	
	private WarningPanel warningPanel;
	
	private UsernamePanel usernamePanel;
	
	private SheepsPositionPanel sheepsPositionPanel;
	
	
	
	private RegistrationFrameObserver observer;
	
	
	
	public RegistrationFrame(int rows, int cols, int sheeps, RegistrationFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* lobby address panel */
		
		lobbyAddressPanel = new LobbyAddressPanel();
		lobbyAddressPanel.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		lobbyAddressPanel.getNextButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToWarningPanel();
			}
		});
		
		/* warning panel */
		
		warningPanel = new WarningPanel();
		warningPanel.getPreviousButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPreviousToLobbyAddressPanel();
			}
		});
		warningPanel.getNextButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToUsernamePanel();
			}
		});
		
		/* username panel */
		
		usernamePanel = new UsernamePanel();
		usernamePanel.getPreviousButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPreviousToWarningPanel();
			}
		});
		usernamePanel.getNextButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToSheepsPositionPanel();
			}
		});
		
		/* sheeps position panel */
		
		sheepsPositionPanel = new SheepsPositionPanel(rows, cols, sheeps);
		sheepsPositionPanel.getPreviousButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPreviousToUsernamePanel();
			}
		});
		sheepsPositionPanel.getRegistrationButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionRegistration();
			}
		});
		
		/* this frame */
		
		add(lobbyAddressPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	
	private void actionExit() {
		if (observer != null)
			observer.onRegistrationFrameExitClick();
	}

	private void actionNextToWarningPanel() {
		if (lobbyAddressPanel.isAddressEmpty())
			MessageFactory.informationDialog(this, "Please, fill the address field");
		else {
			remove(lobbyAddressPanel);
			add(warningPanel, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	private void actionPreviousToLobbyAddressPanel() {
		remove(warningPanel);
		add(lobbyAddressPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void actionNextToUsernamePanel() {
		remove(warningPanel);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void actionPreviousToWarningPanel() {
		remove(usernamePanel);
		add(warningPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void actionNextToSheepsPositionPanel() {
		if (usernamePanel.isUsernameEmpty())
			MessageFactory.informationDialog(this, "Please, fill the username field");
		else {
			remove(usernamePanel);
			add(sheepsPositionPanel, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	private void actionPreviousToUsernamePanel() {
		remove(sheepsPositionPanel);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void actionRegistration() {
		int remainingSheeps = sheepsPositionPanel.getRemainingSheeps();
		if (remainingSheeps == 1)
			MessageFactory.informationDialog(this, "Please, add another " + remainingSheeps + " sheep");
		else if (remainingSheeps != 0)
			MessageFactory.informationDialog(this, "Please, add another " + remainingSheeps + " sheeps");
		else if (observer != null)
			observer.onRegistrationFrameRegistrationClick(
				lobbyAddressPanel.getAddress(),
				usernamePanel.getUsername(),
				sheepsPositionPanel.getSheepsPosition()
			);
	}
	
	
	
	public void lock() {
		lobbyAddressPanel.lock();
		warningPanel.lock();
		usernamePanel.lock();
		sheepsPositionPanel.lock();
	}
	
	public void unlock() {
		lobbyAddressPanel.unlock();
		warningPanel.unlock();
		usernamePanel.unlock();
		sheepsPositionPanel.unlock();
	}
}