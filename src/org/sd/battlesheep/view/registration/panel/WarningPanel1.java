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



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.ViewConst;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class WarningPanel1 extends BSPanel
{
	private JLabel loadingLabel;
	
	
	
	public WarningPanel1() {
		super(ViewConst.BATTLESHIP_BACKGROUND, new BorderLayout());
		
		/* items */
		
		loadingLabel = new JLabel("Loading...", ViewConst.WAITING_ICON, JLabel.CENTER);
		loadingLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		loadingLabel.setBackground(new Color(0, 0, 0, 0));
		loadingLabel.setForeground(Color.WHITE);
		
		/* this panel */
		
		BSPanel middlePanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		middlePanel.add(loadingLabel, BorderLayout.CENTER);
		
		add(middlePanel, BorderLayout.CENTER);
	}
}