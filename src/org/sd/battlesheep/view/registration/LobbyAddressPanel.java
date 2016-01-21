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
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.TransparentPanel;



/**
 * Classe per il pannello che richiede al giocatore di inserire l'indirizzo ip
 * del server (lobby).
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LobbyAddressPanel extends APanel
{
	/*
	 * constants
	 */
	
	private static final Image BATTLESHIP = new ImageIcon(IMGS_PATH + "battleship.jpg").getImage();
	
	
	
	/*
	 * graphic
	 */
	
	private TransparentPanel northPanel;
	
	private JLabel addressLabel;
	
	private JTextField addressTextField;
	
	private TransparentPanel southPanel;
	
	private JButton exitButton;
	
	private JButton nextButton;
	
	
	
	/*
	 * constructor
	 */
	
	public LobbyAddressPanel(int width, int height) {
		super(BATTLESHIP, width, height, new BorderLayout());
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
		addressLabel = new JLabel("Lobby Ip Address:");
		addressLabel.setForeground(Color.WHITE);
		
		addressTextField = new JTextField("127.0.0.1");
		
		northPanel.add(
			addressLabel,
			new GridBagConstraints(
				0, 1, 1, 1, 0.2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 5),
				0, 0
			)
		);
		northPanel.add(
			addressTextField,
			new GridBagConstraints(
				1, 1, 1, 1, 0.8, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 5, 10, 10),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new TransparentPanel(new GridBagLayout());
		
		exitButton = new JButton("Exit");
		
		nextButton = new JButton("Next");
		
		southPanel.add(
			exitButton,
			new GridBagConstraints(
				0, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10),
				0, 0
			)
		);
		southPanel.add(
			nextButton,
			new GridBagConstraints(1, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		addressTextField.setEnabled(false);
	}
	
	@Override
	public void unlock() {
		addressTextField.setEnabled(true);
	}
	
	
	
	/*
	 * model
	 */
	
	public boolean isAddressEmpty() {
		return addressTextField.getText().isEmpty();
	}
	
	public String getAddress() {
		return addressTextField.getText();
	}
	
	
	
	/*
	 * graphic
	 */
	
	public JButton getExitButton() {
		return exitButton;
	}
	
	public JButton getNextButton() {
		return nextButton;
	}
}