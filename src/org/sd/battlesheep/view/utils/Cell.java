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
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.ViewConst;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class Cell extends JLabel
{
	private Image background;
	
	
	
	private int r;
	
	private int c;
	
	
	
	public Cell(int r, int c) {
		setBorder(BorderFactory.createLineBorder(Color.GREEN));
		background = ViewConst.GRASS;
		
		if (r < 0)
			throw new IllegalArgumentException("Row: less than 0");
		this.r = r;
		if (c < 0)
			throw new IllegalArgumentException("Column: less than 0");
		this.c = c;
	}
	
	
	
	public int getRow() {
		return r;
	}
	
	public int getCol() {
		return c;
	}
	
	
	
	public void setGrass() {
		background = ViewConst.GRASS;
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setSheep() {
		background = ViewConst.SHEEP;
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setHitGrass() {
		background = ViewConst.HIT_GRASS;
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void setHitSheep() {
		background = ViewConst.HIT_SHEEP;
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public boolean isGrass() {
		return background.equals(ViewConst.GRASS);
	}
	
	public boolean isSheep() {
		return background.equals(ViewConst.SHEEP);
	}
	
	public boolean isHitGrass() {
		return background.equals(ViewConst.HIT_GRASS);
	}
	
	public boolean isHitSheep() {
		return background.equals(ViewConst.HIT_SHEEP);
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		if (background == null)
			super.paintComponent(g);
		else
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}
}