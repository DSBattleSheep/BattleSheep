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



import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public abstract class AFrame extends JFrame
{
	protected static final String PROGRAM_NAME = "Battlesheep v.0.1 - beta";
	
	protected static final String IMGS_PATH = "./imgs/";
	
	protected static final ImageIcon ICON = new ImageIcon(IMGS_PATH + "icon.png");
	
	
	
	public AFrame(LayoutManager layout) {
		super(PROGRAM_NAME);
		setIconImage(ICON.getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(layout);
	}
	
	public AFrame(int width, int height, LayoutManager layout) {
		super(PROGRAM_NAME);
		setIconImage(ICON.getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setLayout(layout);
	}
	
	
	
	public abstract void lock();
	
	public abstract void unlock();
}