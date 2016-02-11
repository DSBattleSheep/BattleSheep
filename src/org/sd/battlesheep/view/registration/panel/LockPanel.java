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



package org.sd.battlesheep.view.registration.panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.sd.battlesheep.view.BattlesheepPanel;
import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.registration.observer.LockPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LockPanel extends BattlesheepPanel
{
	private JLabel waitingLabel;
	
	private JButton exitButton;
	
	
	
	private LockPanelObserver observer;
	
	
	
	public LockPanel(LockPanelObserver observer) {
		super(new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* items */
		
		waitingLabel = new JLabel("Registration...", WAITING_ICON, JLabel.CENTER);
		waitingLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		waitingLabel.setForeground(Color.WHITE);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		
		/* this panel */
		
		TransparentPanel middlePanel = new TransparentPanel(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		middlePanel.add(waitingLabel, BorderLayout.CENTER);
		
		TransparentPanel southPanel = new TransparentPanel(new GridLayout(1, 2, 10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(exitButton);
		
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	private void actionExit() {
		if (observer != null)
			observer.onLockPanelExitClick();
	}
}