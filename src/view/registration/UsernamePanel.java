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



package view.registration;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.ViewResources;



@SuppressWarnings("serial")
public class UsernamePanel extends JPanel
{
	private JPanel northPanel;
	
	private JLabel playerNameLabel;
	
	private JTextField playerNameField;
	
	private JPanel middlePanel;
	
	private JLabel imageLabel;
	
	
	
	public UsernamePanel() {
		setLayout(new BorderLayout());
		
		initNorthPanel();
		initMiddlePanel();
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
	}
	
	
	
	private void initNorthPanel() {
		northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());
		northPanel.setBackground(Color.WHITE);
		northPanel.setOpaque(true);
		
		playerNameLabel = new JLabel("Player name:");
		playerNameLabel.setBackground(Color.WHITE);
		playerNameLabel.setOpaque(true);
		
		playerNameField = new JTextField("");
		
		northPanel.add(playerNameLabel, new GridBagConstraints(0, 1, 1, 1, 0.3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 0), 0, 0));
		northPanel.add(playerNameField, new GridBagConstraints(1, 1, 1, 1, 0.7, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 10), 0, 0));
	}
	
	private void initMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setBackground(Color.WHITE);
		middlePanel.setOpaque(true);
		
		imageLabel = new JLabel(ViewResources.LOGO);
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setOpaque(true);
		
		middlePanel.add(imageLabel, BorderLayout.CENTER);
	}
}