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



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.sd.battlesheep.view.AFrame;
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
public class RegistrationFrame extends AFrame implements LobbyAddressPanelObserver, UsernamePanelObserver, SheepsPositionPanelObserver, LockPanelObserver
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
		super(WIDTH, HEIGHT);
		
		/* model */
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
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
		
		addMiddlePanel(lobbyAddressPanel);
		setVisible(true);
	}
	
	
	
	@Override
	public void onLobbyAddressPanelExitClick() {
		observer.onRegistrationFrameExitClick();
	}
	
	@Override
	public void onLobbyAddressPanelNextClick(String lobbyAddress) {
		this.lobbyAddress = lobbyAddress;
		replaceMiddlePanel(lobbyAddressPanel, warningPanel1);
		Timer timer1 = new Timer(WARNING_PANEL_1_DURATION, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				replaceMiddlePanel(warningPanel1, warningPanel2);
				Timer timer2 = new Timer(WARNING_PANEL_2_DURATION, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						replaceMiddlePanel(warningPanel2, warningPanel3);
						Timer timer3 = new Timer(WARNING_PANEL_3_DURATION, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								replaceMiddlePanel(warningPanel3, usernamePanel);
							}
						});
						timer3.setRepeats(false);
						timer3.start();
					}
				});
				timer2.setRepeats(false);
				timer2.start();
			}
		});
		timer1.setRepeats(false);
		timer1.start();
	}
	
	@Override
	public void onUsernamePanelPreviousClick() {
		replaceMiddlePanel(usernamePanel, lobbyAddressPanel);
	}
	
	@Override
	public void onUsernamePanelNextClick(String username) {
		this.username = username;
		replaceMiddlePanel(usernamePanel, sheepsPositionPanel);
	}
	
	@Override
	public void onSheepsPositionPanelPreviousClick() {
		replaceMiddlePanel(sheepsPositionPanel, usernamePanel);
	}
	
	@Override
	public void onSheepsPositionPanelRegistrationClick(boolean[][] sheepsPosition) {
		replaceMiddlePanel(sheepsPositionPanel, lockPanel);
		observer.onRegistrationFrameRegistrationClick(lobbyAddress, username, sheepsPosition);
	}
	
	@Override
	public void onLockPanelExitClick() {
		observer.onRegistrationFrameExitClick();
	}
	
	
	
	public void unlock() {
		replaceMiddlePanel(lockPanel, sheepsPositionPanel);
	}
}