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
import java.util.ArrayList;

import org.sd.battlesheep.view.AFrame;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GameFrame extends AFrame
{
	private static final int WIDTH = 800;
	
	private static final int HEIGHT = 600;
	
	
	
	private GamePanel gamePanel;
	
	
	
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
		
		/* game panel */
		
		gamePanel = new GamePanel(rows, cols);
		
		/* this frame */
		
		add(gamePanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	
	public void setTurn(String username) {
		
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		
	}
	
	
	
	@Override
	public void lock() {
		
	}
	
	@Override
	public void unlock() {
		
	}
}