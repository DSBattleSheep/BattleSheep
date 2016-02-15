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



package org.sd.battlesheep.view.game;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import org.sd.battlesheep.view.BSFrame;
import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.game.observer.ListPanelObserver;
import org.sd.battlesheep.view.game.panel.BannerPanel;
import org.sd.battlesheep.view.game.panel.FieldPanel;
import org.sd.battlesheep.view.game.panel.ListPanel;
import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GameFrame extends BSFrame implements ListPanelObserver, FieldObserver
{
	private static final int WIDTH = 800;
	
	private static final int HEIGHT = 600;
	
	
	
	private BannerPanel bannerPanel;
	
	private ListPanel listPanel;
	
	private FieldPanel myFieldPanel;
	
	private FieldPanel opponentFieldPanel;
	
	
	
	private Field myField;
	
	private Field[] opponentsField;
	
	private GameFrameObserver observer;
	
	
	
	/*
	 * constructor
	 */
	
	public GameFrame(String myUsername, String[] opponentsUsername, int rows, int cols, GameFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout(10, 10));
		
		/* model */
		
		this.myField = new Field(rows, cols, myUsername, this);
		
		this.opponentsField = new Field[opponentsUsername.length];
		for (int i = 0; i < this.opponentsField.length; i++)
			this.opponentsField[i] = new Field(rows, cols, opponentsUsername[i], this);
		
		this.observer = observer;
		
		/* panels */
		
		bannerPanel = new BannerPanel();
		
		listPanel = new ListPanel(opponentsField, this);
		
		myFieldPanel = new FieldPanel(myField);
		
		opponentFieldPanel = new FieldPanel(opponentsField[0]);
		
		/* this frame */
		
		BSPanel northPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(bannerPanel);
		
		BSPanel rightPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
		rightPanel.add(listPanel);
		
		BSPanel middlePanel = new BSPanel(new Color(0, 0, 0, 0), new GridLayout(1, 2, 10, 10));
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		BSPanel middleLeftPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		middleLeftPanel.add(myFieldPanel, BorderLayout.CENTER);
		BSPanel middleRightPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		middleRightPanel.add(opponentFieldPanel, BorderLayout.CENTER);
		middlePanel.add(middleLeftPanel);
		middlePanel.add(middleRightPanel);
		
		add(northPanel, BorderLayout.NORTH);
		add(rightPanel, BorderLayout.EAST);
		add(middlePanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	
	
	@Override
	public void onListPanelListValueChanged(Field field) {
		opponentFieldPanel.setField(field);
		SwingUtilities.updateComponentTreeUI(opponentFieldPanel);
	}
	
	@Override
	public void onFieldCellClick(Cell source) {
		source.setSheep();
		SwingUtilities.updateComponentTreeUI(source);
	}
	
	
	
	
	
	
	
	public void setTurn(String username) {
		
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		
	}
	
	public void playerWon(String username) {
		
	}
	
	public void playerCrashed(String username) {
		
	}
	
	public void playerLost(String username, boolean kickedOut) {
		/*
		if (username.equals(myField.getUsername())) {
			if (kickedOut) {
				// sono stato eliminato dalla partita perchè ho laggato troppo e sono arrivato dopo l'inizio del turno
			} else {
				// ho perso perchè mi hanno abbattuto tutte le pecuredde
			}
		} else {
			// Il player 'username' è stato eliminato dalla partita perchè ha perso. 
			// Per ora non viene differenziato se è stato buttato fuori per lag o per aver perso.
		}
		*/
	}
	
	
	
	public void lock() {
		
	}
	
	public void unlock() {
		
	}
}