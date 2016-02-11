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
import javax.swing.Timer;

import org.sd.battlesheep.view.BSFrame;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.registration.observer.LobbyAddressPanelObserver;
import org.sd.battlesheep.view.registration.observer.UsernamePanelObserver;
import org.sd.battlesheep.view.registration.panel.LobbyAddressPanel;
import org.sd.battlesheep.view.registration.panel.LockPanel;
import org.sd.battlesheep.view.registration.panel.SheepsPositionPanel;
import org.sd.battlesheep.view.registration.panel.UsernamePanel;
import org.sd.battlesheep.view.registration.panel.WarningPanel1;
import org.sd.battlesheep.view.registration.panel.WarningPanel2;
import org.sd.battlesheep.view.registration.panel.WarningPanel3;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class RegistrationFrame extends BSFrame implements LobbyAddressPanelObserver, UsernamePanelObserver
{
	private static final int WIDTH = 500;
	
	private static final int HEIGHT = 550;
	
	
	
	private static final int WARNING_PANEL_1_DURATION = 200;// TODO -> 1000
	
	private static final int WARNING_PANEL_2_DURATION = 200;// TODO -> 3000
	
	private static final int WARNING_PANEL_3_DURATION = 200;// TODO -> 2000
	
	
	
	private LobbyAddressPanel lobbyAddressPanel;
	
	private WarningPanel1 warningPanel1;
	
	private WarningPanel2 warningPanel2;
	
	private WarningPanel3 warningPanel3;
	
	private UsernamePanel usernamePanel;
	
	private SheepsPositionPanel sheepsPositionPanel;
	
	private LockPanel lockPanel;
	
	
	
	private String lobbyAddress;
	
	private String username;
	
	private RegistrationFrameObserver observer;
	
	
	
	public RegistrationFrame(int rows, int cols, int sheeps, RegistrationFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* panels */
		
		lobbyAddressPanel = new LobbyAddressPanel(this);
		
		warningPanel1 = new WarningPanel1();
		
		warningPanel2 = new WarningPanel2();
		
		warningPanel3 = new WarningPanel3();
		
		usernamePanel = new UsernamePanel(this);
		
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
		
		/* lock panel */
		
		lockPanel = new LockPanel();
		lockPanel.getExitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
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
	
	
	
	private void actionNextToUsernamePanel() {
		remove(warningPanel3);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
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
		else if (observer != null) {
			actionLock();
			observer.onRegistrationFrameRegistrationClick(
				lobbyAddress,
				username,
				sheepsPositionPanel.getSheepsPosition()
			);
		}
	}
	
	private void actionLock() {
		remove(sheepsPositionPanel);
		add(lockPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	
	public void unlock() {
		remove(lockPanel);
		add(sheepsPositionPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onLobbyAddressPanelExitClick() {
		if (observer != null)
			observer.onRegistrationFrameExitClick();
	}
	
	@Override
	public void onLobbyAddressPanelNextClick(String lobbyAddress) {
		if (lobbyAddress.isEmpty())
			MessageFactory.informationDialog(this, "Please, fill the address field");
		else {
			this.lobbyAddress = lobbyAddress;
			actionNextToWarningPanel1();
		}
	}
	
	private void actionNextToWarningPanel1() {
		remove(lobbyAddressPanel);
		add(warningPanel1, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(WARNING_PANEL_1_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToWarningPanel2();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void actionNextToWarningPanel2() {
		remove(warningPanel1);
		add(warningPanel2, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(WARNING_PANEL_2_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToWarningPanel3();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void actionNextToWarningPanel3() {
		remove(warningPanel2);
		add(warningPanel3, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(WARNING_PANEL_3_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToUsernamePanel();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	@Override
	public void onUsernamePanelPreviousClick() {
		remove(usernamePanel);
		add(lobbyAddressPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onUsernamePanelNextClick(String username) {
		if (username.isEmpty())
			MessageFactory.informationDialog(this, "Please, fill the username field");
		else {
			this.username = username;
			sheepsPositionPanel.setUsername(username);
			remove(usernamePanel);
			add(sheepsPositionPanel, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
}