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
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class BSPanel extends JPanel
{
	private static final String IMGS_PATH;
	
	
	
	protected static final Image BATTLESHIP_BACKGROUND;
	
	protected static final Image BATTLESHEEP_BACKGROUND;
	
	
	
	protected static final Icon WAITING_ICON;
	
	
	
	static {
		IMGS_PATH = "imgs";
		
		BATTLESHIP_BACKGROUND = new ImageIcon(IMGS_PATH + File.separator + "battleship.jpg").getImage();
		BATTLESHEEP_BACKGROUND = new ImageIcon(IMGS_PATH + File.separator + "battlesheep.jpg").getImage();
		
		WAITING_ICON = new ImageIcon(IMGS_PATH + File.separator + "ajax-loader.gif");
	}
	
	
	
	public BSPanel(LayoutManager manager) {
		super(manager);
	}
}