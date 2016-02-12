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



package org.sd.battlesheep.view.lobby.panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.lobby.WaitingPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class WaitingPanel extends BSPanel
{
	private JLabel addressLabel;
	
	private JLabel waitingLabel;
	
	private JButton exitButton;
	
	
	
	private WaitingPanelObserver observer;
	
	
	
	public WaitingPanel(String host, int port, WaitingPanelObserver observer) {
		super(Color.WHITE, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* items */
		
		addressLabel = new JLabel(host + ":" + port, JLabel.CENTER);
		
		waitingLabel = new JLabel("Waiting for clients...", ViewConst.WAITING_ICON, JLabel.CENTER);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		
		/* this panel */
		
		BSPanel northPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(addressLabel, BorderLayout.CENTER);
		
		BSPanel middlePanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		middlePanel.add(waitingLabel, BorderLayout.CENTER);
		
		BSPanel southPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(exitButton, BorderLayout.CENTER);
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	private void actionExit() {
		if (observer != null)
			observer.onWaitingPanelExitClick();
	}
}