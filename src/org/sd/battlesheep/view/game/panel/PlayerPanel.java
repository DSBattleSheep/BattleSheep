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



import java.awt.Container;

import javax.swing.JLabel;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.utils.Field;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class PlayerPanel extends APanel
{
	private JLabel myUsernameLabel;
	
	private JLabel opponentUsernameLabel;
	
	private Container opponentFieldParent;
	
	
	
	public PlayerPanel(Field myField, Field opponentField) {
		super(ViewConst.TRANSPARENT_BACKGROUND);
		
		/* model */
		
		if (myField == null)
			throw new IllegalArgumentException("My field: null object");
		
		if (opponentField == null)
			throw new IllegalArgumentException("Opponent field: null object");
		
		/* items */
		
		myUsernameLabel = new JLabel(myField.getUsername(), JLabel.CENTER);
		
		opponentUsernameLabel = new JLabel(opponentField.getUsername(), JLabel.CENTER);
		
		/* this panel */
		
		addNorthPanel(myUsernameLabel, opponentUsernameLabel);
		addMiddlePanel(myField, opponentField);
		
		opponentFieldParent = opponentField.getParent();
	}
	
	
	
	public void setOpponentField(Field field) {
		if (field == null)
			throw new IllegalArgumentException("Field: null object");
		opponentUsernameLabel.setText(field.getUsername());
		opponentFieldParent.remove(1);
		opponentFieldParent.add(field);
		//SwingUtilities.updateComponentTreeUI(this);
	}
}