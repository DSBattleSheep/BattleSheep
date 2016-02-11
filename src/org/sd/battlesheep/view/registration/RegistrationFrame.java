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
import org.sd.battlesheep.view.registration.observer.LobbyAddressPanelObserver;
import org.sd.battlesheep.view.registration.observer.LockPanelObserver;
import org.sd.battlesheep.view.registration.observer.SheepsPositionPanelObserver;
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
public class RegistrationFrame extends BSFrame implements LobbyAddressPanelObserver, UsernamePanelObserver, SheepsPositionPanelObserver, LockPanelObserver
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
		
		sheepsPositionPanel = new SheepsPositionPanel(rows, cols, sheeps, this);
		
		lockPanel = new LockPanel(this);
		
		/* this frame */
		
		add(lobbyAddressPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	
	@Override
	public void onLobbyAddressPanelExitClick() {
		if (observer != null)
			observer.onRegistrationFrameExitClick();
	}
	
	@Override
	public void onLobbyAddressPanelNextClick(String lobbyAddress) {
		this.lobbyAddress = lobbyAddress;
		nextToWarningPanel1();
	}
	
	private void nextToWarningPanel1() {
		remove(lobbyAddressPanel);
		add(warningPanel1, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(WARNING_PANEL_1_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextToWarningPanel2();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void nextToWarningPanel2() {
		remove(warningPanel1);
		add(warningPanel2, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(WARNING_PANEL_2_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextToWarningPanel3();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void nextToWarningPanel3() {
		remove(warningPanel2);
		add(warningPanel3, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(WARNING_PANEL_3_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextToUsernamePanel();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void nextToUsernamePanel() {
		remove(warningPanel3);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onUsernamePanelPreviousClick() {
		remove(usernamePanel);
		add(lobbyAddressPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onUsernamePanelNextClick(String username) {
		this.username = username;
		remove(usernamePanel);
		add(sheepsPositionPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onSheepsPositionPanelPreviousClick() {
		remove(sheepsPositionPanel);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onSheepsPositionPanelRegistrationClick(boolean[][] sheepsPosition) {
		remove(sheepsPositionPanel);
		add(lockPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		if (observer != null)
			observer.onRegistrationFrameRegistrationClick(lobbyAddress, username, sheepsPosition);
	}
	
	@Override
	public void onLockPanelExitClick() {
		if (observer != null)
			observer.onRegistrationFrameExitClick();
	}
	
	
	
	public void unlock() {
		remove(lockPanel);
		add(sheepsPositionPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
}