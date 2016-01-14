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



package org.sdbattlesheep;



import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class Main extends JFrame
{
	private JLabel label;
	
	private JRadioButton radioServer;
	
	private JRadioButton radioClient;
	
	private JButton buttonExit;
	
	private JButton buttonOk;
	
	
	
	private Main() {
		super("TODO");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		label = new JLabel("Start the program as");
		
		radioServer = new JRadioButton("server", false);
		radioServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioServer.setSelected(true);
				radioClient.setSelected(false);
			}
		});
		
		radioClient = new JRadioButton("client", true);
		radioClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioClient.setSelected(true);
				radioServer.setSelected(false);
			}
		});
		
		buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (radioServer.isSelected())
					JOptionPane.showMessageDialog(null, "Server");// TODO
				else
					JOptionPane.showMessageDialog(null, "Client");// TODO
			}
		});
		
		add(label, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 5, 10), 0, 0));
		add(radioServer, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 5), 0, 0));
		add(radioClient, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 10), 0, 0));
		add(buttonExit, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 10, 5), 0, 0));
		add(buttonOk, new GridBagConstraints(1, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 10), 0, 0));
		
		pack();
		
		setVisible(true);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			
		} catch (InstantiationException ex) {
			
		} catch (IllegalAccessException ex) {
			
		} catch (UnsupportedLookAndFeelException ex) {
			
		}
		
		new Main();
	}
}