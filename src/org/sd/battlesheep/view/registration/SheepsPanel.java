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
import javax.swing.JToggleButton;

import org.sd.battlesheep.Resources;
import org.sd.battlesheep.view.AFrame;



/**
 * Classe per il pannello nel quale è richiesta la posizione iniziale delle
 * pecore del giocatore
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class SheepsPanel extends JPanel
{
	/**
	 * pannello superiore con il label per la posizione delle pecore
	 */
	private JPanel northPanel;
	
	/**
	 * label per la posizione delle pecore
	 */
	private JLabel sheepsPositionLabel;
	
	
	
	/**
	 * pannello centrale con i bottoni per la posizione delle pecore
	 */
	private JPanel middlePanel;
	
	/**
	 * bottoni per la posizione delle pecore
	 */
	private JToggleButton[][] sheepsPositionButtons;
	
	
	
	/**
	 * pannello inferiore con i bottoni per la navigazione
	 */
	private JPanel southPanel;
	
	/**
	 * bottone per l'arretramento
	 */
	private JButton previousButton;
	
	/**
	 * bottone per la registrazione
	 */
	private JButton registrationButton;
	
	
	
	/**
	 * il numero di righe del campo di gioco
	 */
	private int rows;
	
	/**
	 * il numero di colonne del campo di gioco
	 */
	private int cols;
	
	/**
	 * il numero di pecore inserite nel campo di gioco
	 */
	private int sheeps = 0;
	
	/**
	 * l'osservatore delle azioni compiute sul pannello
	 */
	private SheepsPanelObserver observer;
	
	
	
	/**
	 * crea un pannello nel quale è richiesta la posizione iniziale delle
	 * pecore del giocatore
	 * 
	 * @param rows - il numero di righe del campo di gioco
	 * @param cols - il numero di colonne del campo di gioco
	 * @param sheeps - il numero di pecore da inserire nel campo di gioco
	 * @param observer - l'osservatore delle azioni compiute sul pannello
	 */
	public SheepsPanel(int rows, int cols, int sheeps, SheepsPanelObserver observer) {
		
		/*
		 * save the inner objects
		 */
		
		this.rows = rows;
		this.cols = cols;
		this.sheeps = sheeps;
		
		this.observer = observer;
		
		/*
		 * set the panel layout
		 */
		
		setLayout(new BorderLayout());
		
		/*
		 * north panel
		 */
		
		northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setBackground(Color.WHITE);
		northPanel.setOpaque(true);
		
		sheepsPositionLabel = new JLabel("Sheeps position:");
		sheepsPositionLabel.setBackground(Color.WHITE);
		sheepsPositionLabel.setOpaque(true);
		
		northPanel.add(sheepsPositionLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		
		/*
		 * middle panel
		 */
		
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		middlePanel.setBackground(Color.WHITE);
		middlePanel.setOpaque(true);
		
		sheepsPositionButtons = new JToggleButton[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				sheepsPositionButtons[r][c] = new JToggleButton(AFrame.GRASS);
				sheepsPositionButtons[r][c].addActionListener(new ActionListener() {
					@Override public void actionPerformed(ActionEvent e) {
						actionButton((JToggleButton) e.getSource());
					}
				});
				
				// aggiungo un bottone nella prima colonna
				if (c == 0)
					middlePanel.add(sheepsPositionButtons[r][c], new GridBagConstraints(c, r, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
				// aggiungo un bottone nell'ultima colonna
				else if (c == cols - 1)
					middlePanel.add(sheepsPositionButtons[r][c], new GridBagConstraints(c, r, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
				// aggiungo un altro bottone
				else
					middlePanel.add(sheepsPositionButtons[r][c], new GridBagConstraints(c, r, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
		}
		
		/*
		 * south panel
		 */
		
		southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		southPanel.setBackground(Color.WHITE);
		southPanel.setOpaque(true);
		
		previousButton = new JButton("Previous");
		previousButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				actionPrevious();
			}
		});
		
		registrationButton = new JButton("Registration");
		registrationButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				actionRegistration();
			}
		});
		
		southPanel.add(previousButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		southPanel.add(registrationButton, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		
		/*
		 * add the panels to the main panel
		 */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	private void actionButton(JToggleButton source) {
		// voglio aggiungere una pecora ma ho già raggiunto il numero massimo di pecore
		if (source.getIcon().equals(AFrame.GRASS) && sheeps == 0)
			JOptionPane.showMessageDialog(this, "You have already entered all sheeps", AFrame.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
		// voglio aggiungere una pecora e non ho ancora raggiunto il numero massimo di pecore
		else if (source.getIcon().equals(AFrame.GRASS)) {
			source.setIcon(AFrame.SHEEP);
			sheeps--;
		// voglio togliere una pecora
		} else {
			source.setIcon(AFrame.GRASS);
			sheeps++;
		}
	}
	
	private void actionPrevious() {
		observer.onPreviousClick();
	}
	
	private void actionRegistration() {
		// se non sono state inserite tutte le pecore mostra un messaggio di avviso
		if (sheeps != 0)
			JOptionPane.showMessageDialog(this, "You have to enter " + sheeps + " sheeps before proceeding", AFrame.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
		// se snon state inserite tutte le pecore notifica l'osservatore
		else {
			boolean[][] sheeps = new boolean[rows][cols];
			for (int r = 0; r < rows; r++)
				for (int c = 0; c < cols; c++)
					sheeps[r][c] = sheepsPositionButtons[r][c].isSelected() ? true : false;
			observer.onRegistrationClick(sheeps);
		}
	}
}