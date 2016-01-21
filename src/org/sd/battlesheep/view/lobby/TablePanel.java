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
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.TransparentPanel;



@SuppressWarnings("serial")
public class TablePanel extends APanel
{
	/*
	 * graphic
	 */
	
	private TransparentPanel middlePanel;
	
	private JTable clientsTable;
	
	private JScrollPane clientsScrollPane;
	
	private TransparentPanel southPanel;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	/*
	 * model
	 */
	
	
	
	
	
	/*
	 * constructor
	 */
	
	public TablePanel() {
		super(Color.WHITE, new BorderLayout());
		
		/* middle panel */
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
		clientsTable = new JTable();
		// TODO
		clientsScrollPane = new JScrollPane(clientsTable);
		
		middlePanel.add(
			clientsScrollPane,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10),
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
				new Insets(10, 10, 10, 10),
				0, 0
			)
		);
		southPanel.add(
			startButton,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		// add(middlePanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		
	}

	@Override
	public void unlock() {
		
	}
	
	
	
	/*
	 * graphic
	 */
	
	public JButton getExitButton() {
		return exitButton;
	}
	
	public JButton getStartButton() {
		return startButton;
	}
}