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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.Resources;
import org.sd.battlesheep.view.AFrame;



/**
 * Classe per il frame che richiede username e posizione delle pecore nel campo
 * di gioco al giocatore ed effettua la registrazione al server.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class RegistrationFrame extends AFrame implements UsernamePanelObserver, SheepsPanelObserver
{
	/**
	 * costante per la larghezza del frame per la registrazione
	 */
	private static final int WIDTH = 800;
	
	/**
	 * costante per l'altezza del frame per la registrazione
	 */
	private static final int HEIGHT = 600;
	
	
	
	/**
	 * pannello per l'username del giocatore
	 */
	private UsernamePanel usernamePanel;
	
	/**
	 * pannello per la posizione delle pecore nel campo di gioco
	 */
	private SheepsPanel sheepsPanel;
	
	
	
	/**
	 * l'osservatore delle azioni compiute sul frame
	 */
	private RegistrationFrameObserver observer;
	
	
	
	/**
	 * l'username del giocatore
	 */
	private String username;
	
	
	
	/**
	 * crea un frame che richiede username e posizione delle pecore nel campo
	 * di gioco al giocatore ed effettua la registrazione al server
	 * 
	 * @param rows - il numero di righe del campo di gioco
	 * @param cols - il numero di colonne del campo di gioco
	 * @param sheeps - il numero di pecore da inserire nel campo di gioco
	 * @param observer - l'osservatore delle azioni compiute sul frame
	 */
	public RegistrationFrame(int rows, int cols, int sheeps, RegistrationFrameObserver observer) {
		super(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		
		usernamePanel = new UsernamePanel(this);
		sheepsPanel = new SheepsPanel(rows, cols, sheeps, this);
		
		add(usernamePanel, BorderLayout.CENTER);
		
		this.observer = observer;
		
		pack();
		
		setVisible(true);
	}	
	
	
	
	/**
	 * questa funzione viene richiamata in caso di errore nella registrazione 
	 * dal thread incaricato di connettersi alla lobby. 
	 * I casi di errori possono essere:
	 * 1) non è stato possibile connettersi alla lobby (host unreachable)
	 * 2) la lobby è crashata 
	 * 3) lo username scelto è già in uso
	 */
	public void unlockGui() {
		// unlock gui
	}
	
	
	
	@Override
	public void onExitClick() {
		dispose();
	}
	
	@Override
	public void onNextClick(String username) {
		this.username = username;
		remove(usernamePanel);
		add(sheepsPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onPreviousClick() {
		remove(sheepsPanel);
		add(usernamePanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void onRegistrationClick(boolean[][] sheeps) {
		observer.onRegistrationClick(username, sheeps);
	}
}