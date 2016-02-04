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
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	
	
	
	private JPanel middlePanel;
	
	private Field myField;
	
	private Field opponentField;
	
	
	
	private JPanel rightPanel;
	
	private JList<String> opponentsUsernameList;
	
	private ArrayList<Field> opponentsField;
	
	
	
	private JPanel southPanel;
	
	private JTextArea logTextArea;
	
	
	
	private String myUsername;
	
	private String[] opponentsUsername;
	
	private GameFrameObserver observer;
	
	private int rows;
	
	private int cols;
	
	
	
	/*
	 * constructor
	 */
	
	public GameFrame(String myUsername, String[] opponentsUsername, int rows, int cols, GameFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.myUsername = myUsername;
		this.opponentsUsername = opponentsUsername;
		this.observer = observer;
		this.rows = rows;
		this.cols = cols;
		
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
		
		/* middle panel */
		
		middlePanel = new JPanel(new GridBagLayout());
		middlePanel.setBackground(new Color(0, 0, 0, 0));
		
		myField = new Field(myUsername, rows, cols, null);
		
		opponentField = new Field(opponentsUsername[0], rows, cols, null);
		
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
			opponentField,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 5),
				0, 0
			)
		);
		
		/* right panel */
		
		rightPanel = new JPanel(new GridLayout(opponentsUsername.length, 1));
		rightPanel.setBackground(new Color(0, 0, 0, 0));
		
		opponentsUsernameList = new JList<>(opponentsUsername);
		
		opponentsField = new ArrayList<>();
		for (int i = 0; i < opponentsUsername.length; i++)
			opponentsField.add(new Field(opponentsUsername[i], rows, cols, null));
		
		rightPanel.add(
			opponentsUsernameList,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 10),
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
		
		southPanel.add(
			logTextArea,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 10),
				0, 0
			)
		);
		
		/* this frame */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	
	
	private void actionStiCazzi() {
		
	}
	
	
	
	@Override
	public void onFieldCellClick(Cell source) {
		
	}
	
	
	
	public void setTurn(String username) {
		if (username.equals(myUsername)) {
			logTextArea.append("turn: MINE\n");
			
			new Thread(new Runnable() {
				@Override public void run() {
					Random rnd = new Random();
					int u = rnd.nextInt(opponentsUsername.length);
					int x = rnd.nextInt(cols);
					int y = rnd.nextInt(rows);
					logTextArea.append("\tattack " + opponentsUsername[u] + " in [" + x + "," + y + "]\n");
					observer.onGameFrameAttack(opponentsUsername[u], x, y);
				}
			}).start();
			
		} else
			logTextArea.append("turn: " + username + "\n");
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		if (usernameAttacker.equals(myUsername))
			logTextArea.append("\tI " + (hit ? "HIT " : "didn't hit ") + usernameDefender + " in [" + x + "," + y + "]\n");
		else if (usernameDefender.equals(myUsername))
			logTextArea.append("\t" + usernameAttacker + (hit ? " HIT" : " didn't hit") + " ME in [" + x + "," + y + "]\n");
		else
			logTextArea.append("\t" + usernameAttacker + (hit ? " HIT " : " didn't hit ") + usernameDefender + " in [" + x + "," + y + "]\n");
	}
	
	
	
	@Override
	public void lock() {
		
	}
	
	@Override
	public void unlock() {
		
	}
}