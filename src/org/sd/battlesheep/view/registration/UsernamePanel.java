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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sd.battlesheep.Resources;
import org.sd.battlesheep.view.AFrame;



/**
 * Classe per il pannello nel quale è richiesto l'username del giocatore ed è
 * mostrato, al centro, il logo del programma
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class UsernamePanel extends JPanel
{
	/**
	 * pannello superiore con il label ed il campo per l'username del giocatore
	 */
	private JPanel northPanel;
	
	/**
	 * label che richiede di inserire l'username del giocatore
	 */
	private JLabel usernameLabel;
	
	/**
	 * campo per l'inserimento dell'username del giocatore
	 */
	private JTextField usernameField;
	
	
	
	/**
	 * pannello centrale con il label per il logo del programma
	 */
	private JPanel middlePanel;
	
	/**
	 * label con il logo del programma
	 */
	private JLabel imageLabel;
	
	
	
	/**
	 * pannello inferiore con i bottoni per la navigazione
	 */
	private JPanel southPanel;
	
	/**
	 * bottone per l'uscita dal programma
	 */
	private JButton exitButton;
	
	/**
	 * bottone per l'avanzamento
	 */
	private JButton nextButton;
	
	
	
	/**
	 * l'osservatore delle azioni compiute sul pannello
	 */
	private UsernamePanelObserver observer;
	
	
	
	/**
	 * crea un pannello nel quale è richiesto l'username del giocatore ed è
	 * mostrato, al centro, il logo del programma
	 * 
	 * @param observer - l'osservatore delle azioni compiute sul pannello
	 */
	public UsernamePanel(UsernamePanelObserver observer) {
		setLayout(new BorderLayout());
		
		this.observer = observer;
		
		/*
		 * north panel
		 */
		
		northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setBackground(Color.WHITE);
		northPanel.setOpaque(true);
		
		usernameLabel = new JLabel("Player name:");
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setOpaque(true);
		
		usernameField = new JTextField("");
		
		northPanel.add(usernameLabel, new GridBagConstraints(0, 1, 1, 1, 0.3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
		northPanel.add(usernameField, new GridBagConstraints(1, 1, 1, 1, 0.7, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 10), 0, 0));
		
		/*
		 * middle panel
		 */
		
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBackground(Color.WHITE);
		middlePanel.setOpaque(true);
		
		imageLabel = new JLabel(AFrame.LOGO);
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setOpaque(true);
		
		middlePanel.add(imageLabel, BorderLayout.CENTER);
		
		/*
		 * south panel
		 */
		
		southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		southPanel.setBackground(Color.WHITE);
		southPanel.setOpaque(true);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				actionNext();
			}
		});
		
		southPanel.add(exitButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		southPanel.add(nextButton, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		
		/*
		 * add the panels to the main panel
		 */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	/**
	 * funzione per l'azione di click sul bottone "exit"
	 */
	private void actionExit() {
		observer.onExitClick();
	}
	
	/**
	 * funzione per l'azione di click sul bottone "next"
	 */
	private void actionNext() {
		// se non è stato inserito alcun username mostra un messaggio di avviso
		if (usernameField.getText().isEmpty())
			JOptionPane.showMessageDialog(this, "You have to enter your username before proceeding", AFrame.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
		// se è stato inserito un username, notifica l'osservatore
		else
			observer.onNextClick(usernameField.getText());
	}
}