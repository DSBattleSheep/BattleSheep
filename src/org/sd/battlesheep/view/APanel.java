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



import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;



/**
 * Classe astratta che estenderanno tutti i pannelli principali del programma.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public abstract class APanel extends JPanel
{
	/*
	 * constants
	 */
	
	protected static final String IMGS_PATH = "./imgs/";
	
	
	
	/*
	 * model
	 */
	
	private Image background;
	
	private int backgroundWidth;
	
	private int backgroundHeight;
	
	
	
	/*
	 * constructors
	 */
	
	public APanel(LayoutManager layout) {
		super(layout);
		
		this.background = null;
	}
	
	public APanel(Image background, int backgroundWidth, int backgroundHeight, LayoutManager manager) {
		super(manager);
		
		this.background = background;
		this.backgroundWidth = backgroundWidth;
		this.backgroundHeight = backgroundHeight;
	}
	
	
	
	/*
	 * abstract
	 */
	
	public abstract void lock();
	
	public abstract void unlock();
	
	
	
	/*
	 * override
	 */
	
	@Override
	public void paintComponent(Graphics g) {
		if (background == null)
			super.paintComponent(g);
		else
			g.drawImage(background, 0, 0, backgroundWidth, backgroundHeight, null);
	}
}