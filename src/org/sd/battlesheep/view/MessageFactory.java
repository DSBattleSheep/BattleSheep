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



import java.awt.Component;

import javax.swing.JOptionPane;



/**
 * @author Giulio Biagini
 */
public class MessageFactory
{
	private static final String PROGRAM_NAME = "Battlesheep v.0.1 - beta";
	
	
	
	private static void showDialog(Component parent, String message, int type) {
		JOptionPane.showMessageDialog(parent, message, PROGRAM_NAME, type);
	}
	
	
	
	public static void informationDialog(Component parent, String message) {
		showDialog(parent, message, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void errorDialog(Component parent, String message) {
		showDialog(parent, message, JOptionPane.ERROR_MESSAGE);
	}
}