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



package org.sd.battlesheep.view.registration;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import org.sd.battlesheep.view.BattleshipPanel;
import org.sd.battlesheep.view.TransparentPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class WarningPanel2 extends BattleshipPanel
{
	private TransparentPanel middlePanel;
	
	private JLabel hackingLabel;
	
	
	
	public WarningPanel2() {
		super(new BorderLayout());
		
		/* middle panel */
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
		hackingLabel = new JLabel("The game is going to be hacked...", WAITING_ICON, JLabel.CENTER);
		hackingLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		hackingLabel.setBackground(new Color(0, 0, 0, 0));
		hackingLabel.setForeground(Color.RED);
		
		middlePanel.add(
			hackingLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		add(middlePanel, BorderLayout.CENTER);
	}
}