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



import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.utils.Field;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class FieldPanel extends BSPanel
{
	private JLabel usernameLabel;
	
	private Field field;
	
	
	
	public FieldPanel(Field field) {
		super(Color.GREEN, new BorderLayout(10, 10));
		
		/* items */
		
		usernameLabel = new JLabel(field.getUsername().toUpperCase(), JLabel.CENTER);
		
		this.field = field;
		
		/* this panel */
		
		add(usernameLabel, BorderLayout.NORTH);
		add(this.field, BorderLayout.CENTER);
	}
	
	
	
	public void setField(Field field) {
		usernameLabel.setText(field.getUsername());
		this.field = field;
	}
}