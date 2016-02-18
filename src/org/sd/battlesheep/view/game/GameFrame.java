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



import java.awt.Dimension;

import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.game.observer.FieldsListObserver;
import org.sd.battlesheep.view.game.panel.BannerPanel;
import org.sd.battlesheep.view.game.panel.FieldsListPanel;
import org.sd.battlesheep.view.game.panel.PlayerPanel;
import org.sd.battlesheep.view.game.panel.LogPanel;
import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GameFrame extends AFrame implements FieldsListObserver, FieldObserver
{
	private static final int WIDTH = 800;
	
	private static final int HEIGHT = 600;
	
	
	
	private BannerPanel bannerPanel;
	
	private FieldsListPanel fieldsListPanel;
	
	private PlayerPanel playerPanel;
	
	private LogPanel logPanel;
	
	
	
	private GameFrameObserver observer;
	
	
	
	public GameFrame(String myUsername, boolean[][] mySheepsPosition, String[] opponentsUsername, GameFrameObserver observer) {
		super(WIDTH, HEIGHT);
		setBackground(ViewConst.GREEN_BACKGROUND);
		
		/* model */
		
		if (opponentsUsername == null)
			throw new IllegalArgumentException("Usernames: null array");
		if (opponentsUsername.length < 1)
			throw new IllegalArgumentException("Usernames: less than 1");
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* panels */
		
		bannerPanel = new BannerPanel();
		
		playerPanel = new PlayerPanel(myUsername, mySheepsPosition, opponentsUsername[0], this);
		
		fieldsListPanel = new FieldsListPanel(opponentsUsername, mySheepsPosition[0].length, mySheepsPosition.length, this);
		
		logPanel = new LogPanel();
		logPanel.setPreferredSize(new Dimension(getWidth(), 100));
		
		/* this frame */
		
		addNorthPanel(bannerPanel);
		addRightPanel(fieldsListPanel);
		addMiddlePanel(playerPanel);
		addSouthPanel(logPanel);
		setVisible(true);
	}
	
	
	
	@Override
	public void onListValueChanged(Field field) {
		playerPanel.setOpponentField(field);
	}
	
	@Override
	public void onFieldCellClick(String username, Cell source) {
		if (source.isGrass())
			observer.onGameFrameAttack(username, source.getCol(), source.getRow());
	}
	
	
	
	public void setTurn(String username) {
		// it's my turn, unlock cells
		if (playerPanel.getMyUsername().equals(username)) {
			playerPanel.unlockOpponentField();
			logPanel.append("TURNO: MIO\n");
		// it's someone else turn, lock cells
		} else {
			playerPanel.lockOpponentField();
			logPanel.append("TURNO: " + username + "\n");
		}
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		// I'm the defender, change image in my field
		if (playerPanel.getMyUsername().equals(usernameDefender)) {
			if (hit)
				playerPanel.setHitSheepOnMyField(y, x);
			else
				playerPanel.setHitGrassOnMyField(y, x);
		// someone else is the defender, change the image in its field
		} else {
			fieldsListPanel.selectField(usernameDefender);
			if (hit)
				playerPanel.setHitSheepOnOpponentField(y, x);
			else
				playerPanel.setHitGrassOnOpponentField(y, x);
		}
		logPanel.append("\t" + usernameAttacker + " attack " + usernameDefender + " in [" + x + "," + y + "] -> " + (hit ? "HIT" : "don't hit") + "\n");
	}
	
	public void matchResult(int position, boolean kickedOut) {
		// lock the opponent field
		playerPanel.lockOpponentField();
		// show message
		if (kickedOut)
			logPanel.append("HERE IS A NICKEL, KID GET YOURSELF A BETTER CONNECTION\n");
		if (position == 1)
			logPanel.append("I WON!");
		else if (position == 2)
			logPanel.append("SECOND PLACE FOR ME!");
		else if (position == 3)
			logPanel.append("THIRD PLACE FOR ME!");
		else
			logPanel.append("I LOST!");
		MessageFactory.endGameDialog(position);
	}
	
	public void playerLost(String username) {
		// remove field from opponents list
		fieldsListPanel.removeField(username);
		// log and show message
		logPanel.append(username + " LOST!\n");
		MessageFactory.informationDialog(this, username + " LOST!");
	}
	
	public void playerCrashed(String username) {
		// remove field from opponents list
		fieldsListPanel.removeField(username);
		// log and show message
		logPanel.append(username + " CRASHED!\n");
		MessageFactory.informationDialog(this, username + " CRASHED!");
	}
	
	public void playerKickedOut(String username) {
		// remove field from opponents list
		fieldsListPanel.removeField(username);
		// log and show message
		logPanel.append(username + " KICKED OUT!\n");
		MessageFactory.informationDialog(this, username + " KICKED OUT!");
	}
}