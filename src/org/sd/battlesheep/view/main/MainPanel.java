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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.TransparentPanel;



/**
 * Classe per il pannello mostrato nel frame principale che chiede in quale
 * modalità avviare il programma:
 * - come server per la lobby;
 * - come client per il gioco.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class MainPanel extends APanel
{
	/*
	 * graphic
	 */
	
	private TransparentPanel northPanel;
	
	private JLabel modeLabel;
	
	private TransparentPanel middlePanel;
	
	private JRadioButton serverRadioButton;
	
	private JRadioButton clientRadioButton;
	
	private TransparentPanel southPanel;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	/*
	 * constructor
	 */
	
	public MainPanel() {
		super(new BorderLayout());
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
		modeLabel = new JLabel("Start the program as:");
		
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
		
		serverRadioButton = new JRadioButton("server", false);
		serverRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionServer();
			}
		});
		
		clientRadioButton = new JRadioButton("client", true);
		clientRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionClient();
			}
		});
		
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
	
	
	
	/*
	 * actions
	 */
	
	private void actionServer() {
		serverRadioButton.setSelected(true);
		clientRadioButton.setSelected(false);
	}
	
	private void actionClient() {
		clientRadioButton.setSelected(true);
		serverRadioButton.setSelected(false);
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		serverRadioButton.setEnabled(false);
		clientRadioButton.setEnabled(false);
	}
	
	@Override
	public void unlock() {
		serverRadioButton.setEnabled(true);
		clientRadioButton.setEnabled(true);
	}
	
	
	
	/*
	 * model
	 */
	
	public boolean isServerSelected() {
		return serverRadioButton.isSelected();
	}
	
	public boolean isClientSelected() {
		return clientRadioButton.isSelected();
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