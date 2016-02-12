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



package org.sd.battlesheep.view.main.panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.main.observer.MainPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class MainPanel extends BSPanel
{
	private JLabel modeLabel;
	
	private JRadioButton serverRadioButton;
	
	private JRadioButton clientRadioButton;
	
	private ButtonGroup radioButtonGroup;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	private MainPanelObserver observer;
	
	
	
	public MainPanel(MainPanelObserver observer) {
		super(Color.WHITE, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* itmes */
		
		modeLabel = new JLabel("Start the program as:", JLabel.CENTER);
		
		serverRadioButton = new JRadioButton("Server", false);
		serverRadioButton.setBackground(new Color(0, 0, 0, 0));
		serverRadioButton.setMnemonic(KeyEvent.VK_S);
		
		clientRadioButton = new JRadioButton("Client", true);
		clientRadioButton.setBackground(new Color(0, 0, 0, 0));
		clientRadioButton.setMnemonic(KeyEvent.VK_C);
		
		radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(serverRadioButton);
		radioButtonGroup.add(clientRadioButton);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionStart();
			}
		});
		
		/* this panel */
		
		BSPanel northPanel = new BSPanel(new Color(0, 0, 0, 0), new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(modeLabel, BorderLayout.CENTER);
		
		BSPanel middlePanel = new BSPanel(new Color(0, 0, 0, 0), new GridLayout(1, 2, 10, 10));
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		middlePanel.add(serverRadioButton);
		middlePanel.add(clientRadioButton);
		
		BSPanel southPanel = new BSPanel(new Color(0, 0, 0, 0), new GridLayout(1, 2, 10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(exitButton);
		southPanel.add(startButton);
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	private void actionExit() {
		if (observer != null)
			observer.onMainPanelExitClick();
	}
	
	private void actionStart() {
		if (observer != null)
			observer.onMainPanelStartClick(serverRadioButton.isSelected());
	}
}