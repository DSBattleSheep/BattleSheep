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



package org.sd.battlesheep.view.utils;



import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.sd.battlesheep.view.TransparentPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class UsernameField extends Field
{
	private JLabel usernameLabel;
	
	
	
	public UsernameField(String username, int rows, int cols, FieldObserver observer) {
		super(rows, cols, observer);
		
		/* items */
		
		usernameLabel = new JLabel(username, JLabel.CENTER);
		
		/* this panel */
		
		TransparentPanel northPanel = new TransparentPanel(new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		northPanel.add(usernameLabel, BorderLayout.CENTER);
		
		add(northPanel, BorderLayout.NORTH);
	}
}