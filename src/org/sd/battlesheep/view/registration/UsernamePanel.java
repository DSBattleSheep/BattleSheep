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
 * Classe per il pannello che richiede al giocatore di inserire il proprio
 * username.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class UsernamePanel extends APanel
{
	/*
	 * constants
	 */
	
	private static final Image BATTLESHEEP = new ImageIcon(IMGS_PATH + "battlesheep.jpg").getImage();
	
	
	
	/*
	 * graphic
	 */
	
	private TransparentPanel northPanel;
	
	private JLabel usernameLabel;
	
	private JTextField usernameTextField;
	
	private TransparentPanel southPanel;
	
	private JButton previousButton;
	
	private JButton nextButton;
	
	
	
	/*
	 * constructor
	 */
	
	public UsernamePanel(int width, int height) {
		super(BATTLESHEEP, width, height, new BorderLayout());
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(Color.WHITE);
		
		usernameTextField = new JTextField();
		
		northPanel.add(
			usernameLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 0.2, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 5, 5),
				0, 0
			)
		);
		northPanel.add(
			usernameTextField,
			new GridBagConstraints(
				1, 0, 1, 1, 0.8, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 5, 5, 10),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new TransparentPanel(new GridBagLayout());
		
		previousButton = new JButton("Prevoius");
		
		nextButton = new JButton("Next");
		
		southPanel.add(
			previousButton,
			new GridBagConstraints(
				0, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 5),
				0, 0
			)
		);
		southPanel.add(
			nextButton,
			new GridBagConstraints(
				1, 1, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 10, 10),
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
		usernameTextField.setEnabled(false);
	}
	
	@Override
	public void unlock() {
		usernameTextField.setEnabled(true);
	}
	
	
	
	/*
	 * model
	 */
	
	public boolean isUsernameEmpty() {
		return usernameTextField.getText().isEmpty();
	}
	
	public String getUsername() {
		return usernameTextField.getText();
	}
	
	
	
	/*
	 * graphic
	 */
	
	public JButton getPreviousButton() {
		return previousButton;
	}
	
	public JButton getNextButton() {
		return nextButton;
	}
}