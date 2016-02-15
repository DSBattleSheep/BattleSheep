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
import java.awt.Image;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 * @author Giulio Biagini
 */
public class ViewConst
{
	public static final String PROGRAM_NAME = "Battlesheep v.0.1 - beta";
	
	
	
	private static final String IMGS_PATH = "imgs" + File.separator;
	
	
	
	public static final Image PROGRAM_ICON = new ImageIcon(IMGS_PATH + "icon.png").getImage();
	
	public static final Icon WAITING_ICON = new ImageIcon(IMGS_PATH + "ajax-loader.gif");
	
	public static final Icon BANNER_ICON = new ImageIcon(IMGS_PATH + "banner.png");
	
	
	
	public static final Image BATTLESHIP_BACKGROUND = new ImageIcon(IMGS_PATH + "battleship.jpg").getImage();
	
	public static final Image BATTLESHEEP_BACKGROUND = new ImageIcon(IMGS_PATH + "battlesheep.jpg").getImage();
	
	
	
	public static final Color TRANSPARENT_BACKGROUND = new Color(0, 0, 0, 0);
	
	public static final Color WHITE_BACKGROUND = Color.WHITE;
	
	public static final Color GREEN_BACKGROUND = Color.GREEN;
}