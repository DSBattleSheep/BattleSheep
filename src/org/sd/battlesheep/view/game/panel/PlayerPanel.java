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



package org.sd.battlesheep.view.game.panel;



import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class PlayerPanel extends APanel
{
	private JLabel myUsernameLabel;
	
	private JLabel opponentUsernameLabel;
	
	private Field myField;
	
	private Field opponentField;
	
	
	
	public PlayerPanel(String myUsername, boolean[][] mySheepsPosition, String opponentUsername, FieldObserver observer) {
		super(ViewConst.TRANSPARENT_BACKGROUND);
		
		/* model */
		
		if (mySheepsPosition == null)
			throw new IllegalArgumentException("My sheeps position: null matrix");
		if (mySheepsPosition.length < 1)
			throw new IllegalArgumentException("My sheeps position: less than 1");
		if (mySheepsPosition[0].length < 1)
			throw new IllegalArgumentException("My sheeps position: less than 1");
		int cols = mySheepsPosition.length;
		int rows = mySheepsPosition[0].length;
		
		myField = new Field(myUsername, rows, cols, null);
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				if (mySheepsPosition[c][r])
					myField.setSheep(r, c);
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null");
		
		opponentField = new Field(opponentUsername, rows, cols, observer);
		
		/* items */
		
		myUsernameLabel = new JLabel(myUsername, JLabel.CENTER);
		
		opponentUsernameLabel = new JLabel(opponentUsername, JLabel.CENTER);
		
		/* this panel */
		
		addNorthPanel(myUsernameLabel, opponentUsernameLabel);
		addMiddlePanel(myField, opponentField);
	}
	
	
	
	public String getMyUsername() {
		return myField.getUsername();
	}
	
	public void setHitGrassOnMyField(int r, int c) {
		myField.setHitGrass(r, c);
	}
	
	public void setHitSheepOnMyField(int r, int c) {
		myField.setHitSheep(r, c);
	}
	
	public void setOpponentField(Field field) {
		if (field == null)
			throw new IllegalArgumentException("Field: null object");
		if (field.getRows() > opponentField.getRows())
			throw new IllegalArgumentException("Field: too many rows");
		if (field.getRows() < opponentField.getRows())
			throw new IllegalArgumentException("Field: too few rows");
		if (field.getCols() > opponentField.getCols())
			throw new IllegalArgumentException("Field: too many columns");
		if (field.getCols() < opponentField.getCols())
			throw new IllegalArgumentException("Field: too few columns");
		opponentUsernameLabel.setText(field.getUsername());
		opponentField.setUsername(field.getUsername());
		for (int r = 0; r < field.getRows(); r++)
			for (int c = 0; c < field.getCols(); c++)
				if (field.isGrass(r, c))
					opponentField.setGrass(r, c);
				else if (field.isSheep(r, c))
					opponentField.setSheep(r, c);
				else if (field.isHitGrass(r, c))
					opponentField.setHitGrass(r, c);
				else
					opponentField.setHitSheep(r, c);
		opponentField.unlock();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setHitGrassOnOpponentField(int r, int c) {
		opponentField.setHitGrass(r, c);
	}
	
	public void setHitSheepOnOpponentField(int r, int c) {
		opponentField.setHitSheep(r, c);
	}
	
	public void lockOpponentField() {
		opponentField.lock();
	}
	
	public void unlockOpponentField() {
		opponentField.unlock();
	}
}