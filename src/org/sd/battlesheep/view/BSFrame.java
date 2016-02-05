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



import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class BSFrame extends JFrame
{
	protected static final String PROGRAM_NAME = "Battlesheep v.0.1 - beta";
	
	private static final Image ICON = new ImageIcon("imgs/icon.png").getImage();
	
	
	
	public BSFrame(LayoutManager manager) {
		super(PROGRAM_NAME);
		setIconImage(ICON);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(manager);
	}
	
	public BSFrame(int width, int height, LayoutManager layout) {
		super(PROGRAM_NAME);
		setIconImage(ICON);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setLayout(layout);
	}
}