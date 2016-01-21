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



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.sd.battlesheep.view.APanel;



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
	
	private JLabel labelMode;
	
	private JRadioButton radioServer;
	
	private JRadioButton radioClient;
	
	private JButton buttonExit;
	
	private JButton buttonOk;
	
	
	
	/*
	 * constructor
	 */
	
	public MainPanel() {
		super(new GridBagLayout());
		
		labelMode = new JLabel("Start the program as:");
		
		radioServer = new JRadioButton("server", false);
		radioServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionServer();
			}
		});
		
		radioClient = new JRadioButton("client", true);
		radioClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionClient();
			}
		});
		
		buttonExit = new JButton("Exit");
		
		buttonOk = new JButton("OK");
		
		add(labelMode, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 5, 10), 0, 0));
		add(radioServer, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 5), 0, 0));
		add(radioClient, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 10), 0, 0));
		add(buttonExit, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 10, 5), 0, 0));
		add(buttonOk, new GridBagConstraints(1, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 10), 0, 0));
	}
	
	
	
	/*
	 * actions
	 */
	
	private void actionServer() {
		radioServer.setSelected(true);
		radioClient.setSelected(false);
	}
	
	private void actionClient() {
		radioClient.setSelected(true);
		radioServer.setSelected(false);
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		radioServer.setEnabled(false);
		radioClient.setEnabled(false);
	}
	
	@Override
	public void unlock() {
		radioServer.setEnabled(true);
		radioClient.setEnabled(true);
	}
	
	
	
	/*
	 * model
	 */
	
	public boolean isServerSelected() {
		return radioServer.isSelected();
	}
	
	public boolean isClientSelected() {
		return radioClient.isSelected();
	}
	
	
	
	/*
	 * graphic
	 */
	
	public JButton getButtonExit() {
		return buttonExit;
	}
	
	public JButton getButtonOk() {
		return buttonOk;
	}
}