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



package org.sd.battlesheep.view.main;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.WhitePanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class MainPanel extends WhitePanel
{
	private TransparentPanel northPanel;
	
	private JLabel modeLabel;
	
	
	
	private TransparentPanel middlePanel;
	
	private JRadioButton serverRadioButton;
	
	private JRadioButton clientRadioButton;
	
	private ButtonGroup radioButtonGroup;
	
	
	
	private TransparentPanel southPanel;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	public MainPanel() {
		super(new BorderLayout());
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
		modeLabel = new JLabel("Start the program as:", JLabel.CENTER);
		
		northPanel.add(
			modeLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 5, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
		serverRadioButton = new JRadioButton("Server", false);
		serverRadioButton.setBackground(new Color(0, 0, 0, 0));
		serverRadioButton.setMnemonic(KeyEvent.VK_S);
		
		clientRadioButton = new JRadioButton("Client", true);
		clientRadioButton.setBackground(new Color(0, 0, 0, 0));
		clientRadioButton.setMnemonic(KeyEvent.VK_C);
		
		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(serverRadioButton);
		radioButtonGroup.add(clientRadioButton);
		
		middlePanel.add(
			serverRadioButton,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 5),
				0, 0
			)
		);
		
		middlePanel.add(
			clientRadioButton,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 10),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new TransparentPanel(new GridBagLayout());
		
		exitButton = new JButton("Exit");
		
		startButton = new JButton("Start");
		
		southPanel.add(
			exitButton,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 5),
				0, 0
			)
		);
		
		southPanel.add(
			startButton,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	public boolean isServerSelected() {
		return serverRadioButton.isSelected();
	}
	
	public boolean isClientSelected() {
		return clientRadioButton.isSelected();
	}
	
	
	
	public JButton getExitButton() {
		return exitButton;
	}
	
	public JButton getStartButton() {
		return startButton;
	}
}