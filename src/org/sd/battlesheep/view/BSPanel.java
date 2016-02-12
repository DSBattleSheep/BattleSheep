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



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class BSPanel extends JPanel
{
	private Image background;
	
	
	
	public BSPanel(LayoutManager manager) {
		super(manager);
		this.background = null;
	}
	
	public BSPanel(Color background, LayoutManager manager) {
		super(manager);
		setBackground(background);
		this.background = null;
	}
	
	public BSPanel(Image background, LayoutManager manager) {
		super(manager);
		this.background = background;
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		if (background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		else
			super.paintComponent(g);
	}
}