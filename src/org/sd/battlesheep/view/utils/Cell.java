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



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class Cell extends JLabel
{
	private static final String IMGS_PATH = "./imgs/";
	
	private static final Image GRASS = new ImageIcon(IMGS_PATH + "grass.png").getImage();
	
	private static final Image SHEEP = new ImageIcon(IMGS_PATH + "sheep.png").getImage();
	
	private static final Image HIT_GRASS = new ImageIcon(IMGS_PATH + "grass.png").getImage();
	
	private static final Image HIT_SHEEP = new ImageIcon(IMGS_PATH + "sheep.png").getImage();
	
	
	
	private Image background;
	
	
	
	private int r;
	
	private int c;
	
	
	
	public Cell(int r, int c) {
		setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		this.background = GRASS;
		
		this.r = r;
		this.c = c;
	}
	
	
	
	public int getRow() {
		return r;
	}
	
	public int getCol() {
		return c;
	}
	
	
	
	public void setGrass() {
		background = GRASS;
	}
	
	public void setSheep() {
		background = SHEEP;
	}
	
	public void setHitGrass() {
		background = HIT_GRASS;
	}
	
	public void setHitSheep() {
		background = HIT_SHEEP;
	}
	
	public boolean isGrass() {
		return background.equals(GRASS);
	}
	
	public boolean isSheep() {
		return background.equals(SHEEP);
	}
	
	public boolean isHitGrass() {
		return background.equals(HIT_GRASS);
	}
	
	public boolean isHitSheep() {
		return background.equals(HIT_SHEEP);
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		if (background == null)
			super.paintComponent(g);
		else
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}
}