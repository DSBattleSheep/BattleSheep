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



package org.sd.battlesheep.view.registration.panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sd.battlesheep.view.BattleshipPanel;
import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.registration.observer.LobbyAddressPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LobbyAddressPanel extends BattleshipPanel
{
	private JLabel addressLabel;
	
	private JTextField addressTextField;
	
	private JButton exitButton;
	
	private JButton nextButton;
	
	
	
	public LobbyAddressPanel(final LobbyAddressPanelObserver observer) {
		super(new BorderLayout());
		
		/* items */
		
		addressLabel = new JLabel("Lobby Ip Address:");
		addressLabel.setForeground(Color.WHITE);
		
		addressTextField = new JTextField("127.0.0.1");
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (observer != null)
					observer.onLobbyAddressPanelExitClick();
			}
		});
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (observer != null)
					observer.onLobbyAddressPanelNextClick(addressTextField.getText());
			}
		});
		
		/* this panel */
		
		TransparentPanel northPanel = new TransparentPanel(new GridLayout(1, 2, 10, 10));
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(addressLabel);
		northPanel.add(addressTextField);
		
		TransparentPanel southPanel = new TransparentPanel(new GridLayout(1, 2, 10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(exitButton);
		southPanel.add(nextButton);
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	}
}