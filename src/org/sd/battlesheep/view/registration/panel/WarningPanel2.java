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



package org.sd.battlesheep.view.registration.panel;



import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class WarningPanel2 extends APanel
{
	private JLabel hackingLabel;
	
	
	
	public WarningPanel2() {
		super(ViewConst.BATTLESHIP_BACKGROUND);
		
		/* items */
		
		hackingLabel = new JLabel("The game is going to be hacked...", ViewConst.WAITING_ICON, JLabel.CENTER);
		hackingLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		hackingLabel.setForeground(Color.RED);
		
		/* this panel */
		
		addMiddlePanel(hackingLabel);
	}
}