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
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.sd.battlesheep.view.AFrame;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;



/**
 * TODO
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GameFrame extends AFrame
{
	private static final int WIDTH = 800;
	
	private static final int HEIGHT = 600;
	
	
	
	/*
	 * graphic
	 */
	
	private FieldPanel myFieldPanel;
	
	private FieldPanel selectedOpponentFieldPanel;
	
	private FieldPanel[] opponentsFieldPanels;
	
	private JScrollPane opponentsScrollPane;
	
	private JTextArea log;// TODO -> delete this dummy interface
	
	
	
	/*
	 * model
	 */
	
	private String myUsername;
	
	private ArrayList<String> opponentsUsername;
	
	private GameFrameObserver observer;
	
	private int rows;
	
	private int cols;
	
	
	
	/*
	 * constructor
	 */
	
	public GameFrame(String myUsername, ArrayList<String> opponentsUsername, int rows, int cols, GameFrameObserver observer) {
		super(WIDTH, HEIGHT, new BorderLayout());
		
		/* model */
		
		this.myUsername = myUsername;
		this.opponentsUsername = opponentsUsername;
		this.observer = observer;
		this.rows = rows;
		this.cols = cols;
		
		/*
		 * DUMMY
		 */
		
		log = new JTextArea();
		log.setTabSize(4);
		log.setEditable(false);
		log.setBackground(Color.BLACK);
		log.setForeground(Color.YELLOW);
		log.setFont(log.getFont().deriveFont(Font.BOLD));
		
		add(log, BorderLayout.CENTER);
		setVisible(true);
		
		/*
		 * north panel
		 */
		
		// banner
		
		/*
		 * middle panel
		 */
		
		// my field and selected opponent field
		
		/*
		 * south panel
		 */
		
		/* this frame */
		
		// add panels to this frame
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		
	}
	
	@Override
	public void unlock() {
		
	}
	
	
	
	/*
	 * model
	 */
	
	public void setTurn(String username) {
		if (username.equals(myUsername)) {
			log.append("turn: MINE\n");
			
			new Thread(new Runnable() {
				@Override public void run() {
					Random rnd = new Random();
					int u = rnd.nextInt(opponentsUsername.size());
					int x = rnd.nextInt(cols);
					int y = rnd.nextInt(rows);
					log.append("\tattack " + opponentsUsername.get(u) + " in [" + x + "," + y + "]\n");
					observer.onGameFrameAttack(opponentsUsername.get(u), x, y);
				}
			}).start();
			
		} else
			log.append("turn: " + username);
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		if (usernameAttacker.equals(myUsername))
			log.append("\tI " + (hit ? "HIT" : "didn't hit") + usernameDefender + " in [" + x + "," + y + "]\n");
		else if (usernameDefender.equals(myUsername))
			log.append("\t" + usernameAttacker + (hit ? " HIT" : " didn't hit") + " ME in [" + x + "," + y + "]\n");
		else
			log.append("\t" + usernameAttacker + (hit ? " HIT" : " didn't hit") + usernameDefender + " in [" + x + "," + y + "]\n");
	}
}