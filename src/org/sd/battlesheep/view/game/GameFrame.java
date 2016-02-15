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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GameFrame extends AFrame implements FieldObserver
{
	private static final int WIDTH = 800;
	
	private static final int HEIGHT = 600;
	
	
	
	private APanel bannerPanel;
	
	private JLabel bannerLabel;
	
	
	
	private APanel listPanel;
	
	private JLabel opponentsListLabel;
	
	private JList<Field> fieldsList;
	
	private JScrollPane fieldListScrollPane;
	
	
	
	private APanel playerPanel;
	
	private APanel myPanel;
	
	private JLabel myUsernameLabel;
	
	private Field myField;
	
	private APanel opponentPanel;
	
	private JLabel opponentUsernameLabel;
	
	private Field[] opponentsField;
	
	
	
	private GameFrameObserver observer;
	
	
	
	/*
	 * constructor
	 */
	
	public GameFrame(String myUsername, String[] opponentsUsername, int rows, int cols, GameFrameObserver observer) {
		super(WIDTH, HEIGHT);
		
		/* model */
		
		if (myUsername == null)
			throw new IllegalArgumentException("My username: null string");
		if (myUsername.isEmpty())
			throw new IllegalArgumentException("My username: empty string");
		myField = new Field(myUsername, rows, cols, this);
		
		if (opponentsUsername == null)
			throw new IllegalArgumentException("Opponents username: null array");
		if (opponentsUsername.length < 1)
			throw new IllegalArgumentException("Opponents username: less than 1");
		opponentsField = new Field[opponentsUsername.length];
		for (int i = 0; i < opponentsUsername.length; i++) {
			if (opponentsUsername[i] == null)
				throw new IllegalArgumentException("Opponent username " + (i + 1) + ": null string");
			if (opponentsUsername[i].isEmpty())
				throw new IllegalArgumentException("Opponent username " + (i + 1) + ": empty string");
			opponentsField[i] = new Field(opponentsUsername[i], rows, cols, this);
		}
		
		/*
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");*/// TODO - DECOMMENTARE!
		this.observer = observer;
		
		/* banner panel */
		
		bannerPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		
		bannerLabel= new JLabel(ViewConst.BANNER_ICON, JLabel.CENTER);
		
		bannerPanel.addNorthPanel(bannerLabel);
		
		/* list panel */
		
		listPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		
		opponentsListLabel = new JLabel("Opponents", JLabel.CENTER);
		
		fieldsList = new JList<Field>(opponentsField);
		fieldsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// neither press nor adjust event but release event
				if (!fieldsList.getValueIsAdjusting())
					actionList();
			}
		});
		
		fieldListScrollPane = new JScrollPane(fieldsList);
		
		listPanel.addNorthPanel(opponentsListLabel);
		listPanel.addMiddlePanel(fieldListScrollPane);
		
		/* player panel */
		
		playerPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		
		myPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		
		myUsernameLabel = new JLabel(myField.getUsername(), JLabel.CENTER);
		
		myPanel.addNorthPanel(myUsernameLabel);
		myPanel.addMiddlePanel(myField);
		
		opponentPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		
		opponentUsernameLabel = new JLabel(opponentsField[0].getUsername(), JLabel.CENTER);
		
		opponentPanel.addNorthPanel(opponentUsernameLabel);
		opponentPanel.addMiddlePanel(opponentsField[0]);
		
		playerPanel.addMiddlePanel(myPanel, opponentPanel);
		
		/* this frame */
		
		add(bannerPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.EAST);
		add(playerPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	
	private void actionList() {
		int index = fieldsList.getSelectedIndex();
		if (index != -1) {
			opponentUsernameLabel.setText(opponentsField[index].getUsername());
			opponentPanel.remove(1);
			opponentPanel.add(opponentsField[index]);
			SwingUtilities.updateComponentTreeUI(opponentPanel);
		}
	}
	
	@Override
	public void onFieldCellClick(String username, Cell source) {
		if (source.isGrass())
			source.setSheep();
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