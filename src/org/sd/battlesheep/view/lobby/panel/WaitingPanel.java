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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.WhitePanel;
import org.sd.battlesheep.view.lobby.WaitingPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class WaitingPanel extends WhitePanel
{
	private JLabel addressLabel;
	
	private JLabel waitingLabel;
	
	private JButton exitButton;
	
	
	
	public WaitingPanel(String host, int port, final WaitingPanelObserver observer) {
		super(new BorderLayout());
		
		/* items */
		
		addressLabel = new JLabel(host + ":" + port, JLabel.CENTER);
		
		waitingLabel = new JLabel("Waiting for clients...", WAITING_ICON, JLabel.CENTER);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (observer != null)
					observer.onWaitingPanelExitClick();
			}
		});
		
		/* this panel */
		
		TransparentPanel northPanel = new TransparentPanel(new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(addressLabel, BorderLayout.CENTER);
		
		TransparentPanel middlePanel = new TransparentPanel(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		middlePanel.add(waitingLabel, BorderLayout.CENTER);
		
		TransparentPanel southPanel = new TransparentPanel(new BorderLayout());
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(exitButton, BorderLayout.CENTER);
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}