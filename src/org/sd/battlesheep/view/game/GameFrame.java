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
import java.util.ArrayList;

import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.game.observer.FieldsListObserver;
import org.sd.battlesheep.view.game.panel.BannerPanel;
import org.sd.battlesheep.view.game.panel.PlayersListPanel;
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
	
	private PlayersListPanel playersListPanel;
	
	private PlayerPanel playerPanel;
	
	private LogPanel logPanel;
	
	
	
	private Field myField;
	
	private ArrayList<Field> opponentsField;
	
	private GameFrameObserver observer;
	
	
	
	public GameFrame(String myUsername, boolean[][] mySheepsPosition, String[] opponentsUsername, GameFrameObserver observer) {
		super(WIDTH, HEIGHT);
		setBackground(ViewConst.GREEN_BACKGROUND);
		
		/* model */
		
		myField = new Field(myUsername, mySheepsPosition, this);
		myField.lock();
		
		if (opponentsUsername == null)
			throw new IllegalArgumentException("Usernames: null array");
		if (opponentsUsername.length < 1)
			throw new IllegalArgumentException("Usernames: less than 1");
		opponentsField = new ArrayList<>();
		for (int i = 0; i < opponentsUsername.length; i++) {
			opponentsField.add(new Field(opponentsUsername[i], myField.getCols(), myField.getRows(), this));
			opponentsField.get(i).lock();
		}
			
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* panels */
		
		bannerPanel = new BannerPanel();
		
		playerPanel = new PlayerPanel(myField, opponentsField.get(0));
		
		playersListPanel = new PlayersListPanel(opponentsUsername, this);
		
		logPanel = new LogPanel();
		logPanel.setPreferredSize(new Dimension(getWidth(), 100));
		
		/* this frame */
		
		addNorthPanel(bannerPanel);
		addRightPanel(playersListPanel);
		addMiddlePanel(playerPanel);
		addSouthPanel(logPanel);
		setVisible(true);
	}
	
	
	
	@Override
	public void onListValueChanged(int fieldIndex) {
		playerPanel.setOpponentField(opponentsField.get(fieldIndex));
	}
	
	@Override
	public void onFieldCellClick(String username, Cell source) {
		if (source.isGrass())
			observer.onGameFrameAttack(username, source.getPosX(), source.getPosY());
	}
	
	
	
	public void setTurn(String username) {
		// it's my turn, unlock opponents cells
		if (myField.getUsername().equals(username)) {
			for (Field opponentField : opponentsField)
				opponentField.unlock();
			logPanel.append("TURNO: MIO\n");
		// it's someone else turn, lock cells
		} else {
			for (Field opponentField : opponentsField)
				opponentField.lock();
			logPanel.append("TURNO: " + username + "\n");
		}
	}
	
	public void attackResult(String usernameAttacker, String usernameDefender, int x, int y, boolean hit) {
		// I'm the defender, change image in my field
		if (myField.getUsername().equals(usernameDefender)) {
			if (hit)
				myField.setHitSheep(x, y);
			else
				myField.setHitGrass(x, y);
		// someone else is the defender, change image in its field and show it
		} else
			for (int i = 0; i < opponentsField.size(); i++)
				if (opponentsField.get(i).getUsername().equals(usernameDefender)) {
					if (hit)
						opponentsField.get(i).setHitSheep(x, y);
					else
						opponentsField.get(i).setHitGrass(x, y);
					playersListPanel.selectUsername(i);
					break;
				}
		logPanel.append("\t" + usernameAttacker + " attack " + usernameDefender + " in [" + x + "," + y + "] -> " + (hit ? "HIT" : "don't hit") + "\n");
	}
	
	public void matchResult(int position, boolean kickedOut) {
		// lock opponents field
		for (Field opponentField :opponentsField)
			opponentField.lock();
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
		for (int i = 0; i < opponentsField.size(); i++)
			if (opponentsField.get(i).getUsername().equals(username)) {
				opponentsField.remove(i);
				playersListPanel.replaceUsernames(opponentsField);
				break;
			}
		// log and show message
		logPanel.append(username + " LOST!\n");
		MessageFactory.informationDialog(this, username + " LOST!");
	}
	
	public void playerCrashed(String username) {
		// remove field from opponents list
		for (int i = 0; i < opponentsField.size(); i++)
			if (opponentsField.get(i).getUsername().equals(username)) {
				opponentsField.remove(i);
				playersListPanel.replaceUsernames(opponentsField);
				break;
			}
		// log and show message
		logPanel.append(username + " CRASHED!\n");
		MessageFactory.informationDialog(this, username + " CRASHED!");
	}
	
	public void playerKickedOut(String username) {
		// remove field from opponents list
		for (int i = 0; i < opponentsField.size(); i++)
			if (opponentsField.get(i).getUsername().equals(username)) {
				opponentsField.remove(i);
				playersListPanel.replaceUsernames(opponentsField);
				break;
			}
		// log and show message
		logPanel.append(username + " KICKED OUT!\n");
		MessageFactory.informationDialog(this, username + " KICKED OUT!");
	}
}