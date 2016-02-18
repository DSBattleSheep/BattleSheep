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
import java.awt.Dialog.ModalityType;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



/**
 * @author Giulio Biagini
 */
public class MessageFactory
{
	private static void showDialog(final Component parent, final String message, final int type) {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				JOptionPane.showMessageDialog(parent, message, ViewConst.PROGRAM_NAME, type);
			}
		}).start();
		
	}
	
	public static void informationDialog(Component parent, String message) {
		showDialog(parent, message, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void errorDialog(Component parent, String message) {
		showDialog(parent, message, JOptionPane.ERROR_MESSAGE);
	}
	
	
	public static void endGameDialog(int position) {
		JDialog dialog = new JDialog();
		JLabel label;
		switch (position) {
			case 1:
				dialog.setTitle("You Win!");
				label = new JLabel(ViewConst.IMG_1_PLACE);	
				break;
			case 2:
				dialog.setTitle("You were GREAT, but..!");
				label = new JLabel(ViewConst.IMG_2_PLACE);
				break;
			case 3:
				dialog.setTitle("Third place.. Too bad!");
				label = new JLabel(ViewConst.IMG_3_PLACE);
				break;
			default:
				dialog.setTitle("Let's face it.. You lost this fight!");
				label = new JLabel(ViewConst.IMG_LOST);
		}
		dialog.add(label);
		dialog.pack();
		dialog.setLocationByPlatform(true);
		dialog.setModalityType(ModalityType.TOOLKIT_MODAL);
		dialog.setVisible(true);     
	}
}