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
	
	private WarningPanel1 warningPanel1;
	
	private WarningPanel2 warningPanel2;
	
	private WarningPanel3 warningPanel3;
	
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
				actionNextToWarningPanel1();
			}
		});
		
		/* warning panel 1 */
		
		warningPanel1 = new WarningPanel1();
		
		/* warning panel 2 */
		
		warningPanel2 = new WarningPanel2();
		
		/* warning panel 3 */
		
		warningPanel3 = new WarningPanel3();
		
		/* username panel */
		
		usernamePanel = new UsernamePanel();
		usernamePanel.getPreviousButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPreviousToLobbyAddressPanel();
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
	
	private void actionNextToWarningPanel1() {
		if (lobbyAddressPanel.isAddressEmpty())
			MessageFactory.informationDialog(this, "Please, fill the address field");
		else {
			remove(lobbyAddressPanel);
			add(warningPanel1, BorderLayout.CENTER);
			SwingUtilities.updateComponentTreeUI(this);
			Timer timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actionNextToWarningPanel2();
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}
	
	private void actionNextToWarningPanel2() {
		remove(warningPanel1);
		add(warningPanel2, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Timer timer = new Timer(3000, new ActionListener() {
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
		Timer timer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNextToUsernamePanel();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void actionNextToUsernamePanel() {
		remove(warningPanel3);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void actionPreviousToLobbyAddressPanel() {
		remove(usernamePanel);
		add(lobbyAddressPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void actionNextToSheepsPositionPanel() {
		if (usernamePanel.isUsernameEmpty())
			MessageFactory.informationDialog(this, "Please, fill the username field");
		else {
			sheepsPositionPanel.setUsername(usernamePanel.getUsername());
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
	
	
	
	@Override
	public void lock() {
		lobbyAddressPanel.lock();
		warningPanel1.lock();
		warningPanel2.lock();
		warningPanel3.lock();
		usernamePanel.lock();
		sheepsPositionPanel.lock();
	}
	
	@Override
	public void unlock() {
		lobbyAddressPanel.unlock();
		warningPanel1.unlock();
		warningPanel2.unlock();
		warningPanel3.unlock();
		usernamePanel.unlock();
		sheepsPositionPanel.unlock();
	}
}