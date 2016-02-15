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



package org.sd.battlesheep.view;



import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public abstract class AFrame extends JFrame
{
	public AFrame() {
		super(ViewConst.PROGRAM_NAME);
		setIconImage(ViewConst.PROGRAM_ICON);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}
	
	public AFrame(int width, int height) {
		super(ViewConst.PROGRAM_NAME);
		setIconImage(ViewConst.PROGRAM_ICON);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}
	
	
	
	public void addPanel(APanel panel) {
		add(panel, BorderLayout.CENTER);
	}
	
	public void replacePanel(APanel oldPanel, APanel newPanel) {
		remove(oldPanel);
		add(newPanel, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
	}
}