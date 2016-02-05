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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.WhitePanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class WaitingPanel extends WhitePanel
{
	private TransparentPanel northPanel;
	
	private JLabel addressLabel;
	
	
	
	private TransparentPanel middlePanel;
	
	private JLabel waitingLabel;
	
	
	
	private TransparentPanel southPanel;
	
	private JButton exitButton;
	
	
	
	public WaitingPanel(String host, int port) {
		super(new BorderLayout());
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
		addressLabel = new JLabel(host + ":" + port, JLabel.CENTER);
		
		northPanel.add(
			addressLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 5, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
		waitingLabel = new JLabel("Waiting for clients...", WAITING_ICON, JLabel.CENTER);
		
		middlePanel.add(
			waitingLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 10),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new TransparentPanel(new GridBagLayout());
		
		exitButton = new JButton("Exit");
		
		southPanel.add(
			exitButton,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	public JButton getExitButton() {
		return exitButton;
	}
}