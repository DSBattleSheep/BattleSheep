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

import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GameFrame extends AFrame implements FieldObserver
{
	private static final Icon BANNER = new ImageIcon(IMGS_PATH + "banner.png");
	
	
	
	private static final int WIDTH = 800;
	
	private static final int HEIGHT = 600;
	
	
	
	private JPanel northPanel;
	
	private JLabel bannerLabel;
	
	
	
	private JPanel rightPanel;
	
	private ArrayList<Field> opponentsField;
	
	private JList<String> opponentsUsernameList;
	
	
	
	private JPanel middlePanel;
	
	private Field myField;
	
	private Field opponentField;
	
	
	
	private JPanel southPanel;
	
	private JTextArea logTextArea;
	
	private JScrollPane logScrollPane;
	
	
	
	private GameFrameObserver observer;
	
	
	
	/*
	 * constructor
	 */
	
	public GameFrame(String myUsername, String[] opponentsUsername, int rows, int cols, GameFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* north panel */
		
		northPanel = new JPanel(new GridBagLayout());
		northPanel.setBackground(new Color(0, 0, 0, 0));
		
		bannerLabel = new JLabel(BANNER);
		bannerLabel.setOpaque(true);
		bannerLabel.setBackground(new Color(0, 0, 0, 0));
		
		northPanel.add(
			bannerLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 5, 10),
				0, 0
			)
		);
		
		/* right panel */
		
		rightPanel = new JPanel(new GridLayout(opponentsUsername.length, 1));
		rightPanel.setBackground(new Color(0, 0, 0, 0));
		
		opponentsField = new ArrayList<>();
		for (String opponentUsername : opponentsUsername)
			opponentsField.add(new Field(opponentUsername, rows, cols, this));
		
		opponentsUsernameList = new JList<>(opponentsUsername);
		opponentsUsernameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actionList();
			}
		});
		
		rightPanel.add(
			opponentsUsernameList,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new JPanel(new GridBagLayout());
		middlePanel.setBackground(new Color(0, 0, 0, 0));
		
		myField = new Field(myUsername, rows, cols, null);
		
		opponentField = opponentsField.get(0);
		
		middlePanel.add(
			myField,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 5),
				0, 0
			)
		);
		
		middlePanel.add(
			opponentsField.get(0),
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 5),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new JPanel(new GridBagLayout());
		southPanel.setBackground(new Color(0, 0, 0, 0));
		southPanel.setPreferredSize(new Dimension(southPanel.getWidth(), 75));
		
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setBackground(Color.BLACK);
		logTextArea.setForeground(Color.WHITE);
		logTextArea.setSelectionColor(Color.WHITE);
		logTextArea.setSelectedTextColor(Color.BLACK);
		
		DefaultCaret caret = (DefaultCaret) logTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		logScrollPane = new JScrollPane(logTextArea);
		
		southPanel.add(
			logScrollPane,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 10),
				0, 0
			)
		);
		
		/* this frame */
		
		add(northPanel, BorderLayout.NORTH);
		add(rightPanel, BorderLayout.EAST);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	
	
	private void actionList() {
		String opponentUsername = opponentsUsernameList.getSelectedValue();
		if (opponentUsername != null) {
			for (Field field : opponentsField)
				if (field.getUsername().equals(opponentUsername)) {
					middlePanel.remove(1);
					middlePanel.add(
						field,
						new GridBagConstraints(
							1, 0, 1, 1, 1, 1,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(5, 10, 5, 5),
							0, 0
						)
					);
					break;
				}
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	
	
	@Override
	public void onFieldCellClick(Cell source) {
		if (source.isGrass()) {
			String opponentUsername = opponentField.getUsername();
			int r = source.getRow();
			int c = source.getCol();
			logTextArea.append("\tI attack " + opponentUsername + " in [" + c + "," + r + "]\n");
			if (observer != null)
				observer.onGameFrameAttack(opponentUsername, c, r);
		}
	}
	
	
	
	public void setTurn(String username) {
		// it's my turn
		if (username.equals(myField.getUsername())) {
			opponentsUsernameList.setEnabled(true);
			opponentField.unlock();
			logTextArea.append("TURN: mine\n");
		// it's someone else turn
		} else {
			opponentsUsernameList.setEnabled(false);
			opponentField.lock();
			logTextArea.append("TURN: " + username + "\n");
		}
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		// I'm the attacker
		if (usernameAttacker.equals(myField.getUsername()))
			logTextArea.append("\tI " + (hit ? "HIT " : "didn't hit ") + usernameDefender + " in [" + x + "," + y + "]\n");
		// I'm the defender
		else if (usernameDefender.equals(myField.getUsername())) {
			logTextArea.append("\t" + usernameAttacker + " attack ME in [" + x + "," + y + "]\n");
			logTextArea.append("\t" + usernameAttacker + (hit ? " HIT" : " didn't hit") + " ME in [" + x + "," + y + "]\n");
		// someone attacked someone else
		} else {
			logTextArea.append("\t" + usernameAttacker + " attack " + usernameDefender + " in [" + x + "," + y + "]\n");
			logTextArea.append("\t" + usernameAttacker + (hit ? " HIT " : " didn't hit ") + usernameDefender + " in [" + x + "," + y + "]\n");
		}
		
		// I'm the defender
		if (usernameAttacker.equals(myField.getUsername())) {
			if (hit)
				myField.getCell(y, x).setHitSheep();
			else
				myField.getCell(y, x).setHitGrass();
		// someone else is the defender
		} else {
			for (int i = 0; i < opponentsField.size(); i++)
				if (opponentsField.get(i).getUsername().equals(usernameDefender)) {
					if (hit)
						opponentsField.get(i).getCell(y, x).setHitSheep();
					else
						opponentsField.get(i).getCell(y, x).setHitGrass();
					opponentField = opponentsField.get(i);
					SwingUtilities.updateComponentTreeUI(this);
					break;
				}
		}
	}
	
	
	
	@Override
	public void lock() {
		
	}
	
	@Override
	public void unlock() {
		
	}
}