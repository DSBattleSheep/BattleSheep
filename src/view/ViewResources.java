/*
 * Battlesheep is a funny remake of the famous BattleShip game, developed
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



package view;



import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 * Classe per le costanti della vista.
 * 
 * @author Giulio Biagini
 */
public class ViewResources
{
	/**
	 * costante per il nome del programma
	 */
	public static final String PROGRAM_NAME = "Battlesheep v.0.1 - beta";
	
	
	
	/**
	 * costante per il path della cartella delle immagini
	 */
	private static final String IMGS_PATH = "./imgs/";
	
	/**
	 * costante per il path dell'immagine del logo
	 */
	private static final String LOGO_PATH = IMGS_PATH + "logo.png";
	
	/**
	 * costante per il path dell'immagine dell'erba
	 */
	private static final String GRASS_PATH = IMGS_PATH + "grass.png";
	
	/**
	 * costante per il path dell'immagine della pecora
	 */
	private static final String SHEEP_PATH = IMGS_PATH + "sheep.png";
	
	
	
	/**
	 * costante per l'icona del logo
	 */
	public static final Icon LOGO = new ImageIcon(LOGO_PATH);
	
	/**
	 * costante per l'icona dell'erba
	 */
	public static final Icon GRASS = new ImageIcon(GRASS_PATH);
	
	/**
	 * costante per l'icona della pecora
	 */
	public static final Icon SHEEP = new ImageIcon(SHEEP_PATH);
}