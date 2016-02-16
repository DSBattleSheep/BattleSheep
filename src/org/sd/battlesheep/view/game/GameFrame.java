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
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.MessageFactory;
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
	
	private ArrayList<Field> opponentsField;
	
	
	
	private APanel logPanel;
	
	private JTextArea logTextArea;
	
	private JScrollPane logScrollPane;
	
	
	
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
		myField = new Field(myUsername, rows, cols, null);
		
		if (opponentsUsername == null)
			throw new IllegalArgumentException("Opponents username: null array");
		if (opponentsUsername.length < 1)
			throw new IllegalArgumentException("Opponents username: less than 1");
		opponentsField = new ArrayList<>();
		for (int i = 0; i < opponentsUsername.length; i++) {
			if (opponentsUsername[i] == null)
				throw new IllegalArgumentException("Opponent username " + (i + 1) + ": null string");
			if (opponentsUsername[i].isEmpty())
				throw new IllegalArgumentException("Opponent username " + (i + 1) + ": empty string");
			opponentsField.add(new Field(opponentsUsername[i], rows, cols, this));
		}
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* banner panel */
		
		bannerLabel= new JLabel(ViewConst.BANNER_ICON, JLabel.CENTER);
		
		bannerPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		bannerPanel.addNorthPanel(bannerLabel);
		
		/* list panel */
		
		opponentsListLabel = new JLabel("Opponents", JLabel.CENTER);
		
		fieldsList = new JList<Field>(opponentsField.toArray(new Field[opponentsField.size()]));
		fieldsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// neither press nor adjust event but release event
				if (!fieldsList.getValueIsAdjusting())
					actionList();
			}
		});
		
		fieldListScrollPane = new JScrollPane(fieldsList);
		
		listPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		listPanel.addNorthPanel(opponentsListLabel);
		listPanel.addMiddlePanel(fieldListScrollPane);
		
		/* player panel */
		
		myUsernameLabel = new JLabel(myField.getUsername(), JLabel.CENTER);
		
		opponentUsernameLabel = new JLabel(opponentsField.get(0).getUsername(), JLabel.CENTER);
		
		myPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		myPanel.addNorthPanel(myUsernameLabel);
		myPanel.addMiddlePanel(myField);
		
		opponentPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		opponentPanel.addNorthPanel(opponentUsernameLabel);
		opponentPanel.addMiddlePanel(opponentsField.get(0));
		
		playerPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		playerPanel.addMiddlePanel(myPanel, opponentPanel);
		
		/* log panel */
		
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setBackground(Color.BLACK);
		logTextArea.setForeground(Color.GREEN);
		
		((DefaultCaret) logTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		logScrollPane = new JScrollPane(logTextArea);
		
		logPanel = new APanel(ViewConst.TRANSPARENT_BACKGROUND) {};
		logPanel.setPreferredSize(new Dimension(getWidth(), 100));
		logPanel.addMiddlePanel(logScrollPane);
		
		/* this frame */
		
		add(bannerPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.EAST);
		add(playerPanel, BorderLayout.CENTER);
		add(logPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	
	
	private void actionList() {
		int index = fieldsList.getSelectedIndex();
		if (index != -1) {
			opponentUsernameLabel.setText(opponentsField.get(index).getUsername());
			opponentPanel.remove(1);
			opponentPanel.addMiddlePanel(opponentsField.get(index));
			SwingUtilities.updateComponentTreeUI(opponentPanel);
		}
	}
	
	@Override
	public void onFieldCellClick(String username, Cell source) {
		if (source.isGrass())
			observer.onGameFrameAttack(username, source.getCol(), source.getRow());
	}
	
	
	
	public void setTurn(String username) {
		if (username.equals(myField.getUsername())) {
			for (Field opponentField : opponentsField)
				opponentField.unlock();
			logTextArea.append("TURNO: MIO\n");
		} else {
			for (Field opponentField : opponentsField)
				opponentField.lock();
			logTextArea.append("TURNO: " + username + "\n");
		}
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		logTextArea.append("\t" + usernameAttacker + " attack " + usernameDefender + " in [" + x + "," + y + "] -> " + (hit ? "HIT" : "don't hit") + "\n");
		// I'm the defender, change image in my field
		if (usernameDefender.equals(myField.getUsername())) {
			if (hit)
				myField.setHitSheep(y, x);
			else
				myField.setHitGrass(y, x);
		// someone else is the defender, chenge the image in its field and show it
		} else
			for (int i = 0; i < opponentsField.size(); i++)
				if (opponentsField.get(i).getUsername().equals(usernameDefender)) {
					if (hit)
						opponentsField.get(i).setHitSheep(y, x);
					else
						opponentsField.get(i).setHitGrass(y, x);
					fieldsList.setSelectedIndex(i);
					actionList();
				}
	}
	
	public void playerWon(String username) {
		// lock the fields
		for (Field opponentField : opponentsField)
			opponentField.lock();
		// I'm the winner
		if (myField.getUsername().equals(username)) {
			logTextArea.append("I WON!");
			MessageFactory.informationDialog(this, "I WON!");
		// someone else is the winner
		} else {
			logTextArea.append(username + " WON!");
			MessageFactory.informationDialog(this, username + " WON!");
		}
	}
	
	public void playerLost(String username, int position, boolean kickedOut) {
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
	}
	
	public void playerCrashed(String username) {
		// remove field from opponentsField
		for (Field opponentField : opponentsField)
			if (opponentField.getUsername().equals(username)) {
				opponentsField.remove(opponentField);
				break;
			}
		// update list
		fieldsList.setListData(opponentsField.toArray(new Field[opponentsField.size()]));
		// log and show message
		logTextArea.append(username + " CRASHED!");
		MessageFactory.informationDialog(this, username + " CRASHED!");
	}
}