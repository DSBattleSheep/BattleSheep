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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.registration.observer.UsernamePanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class UsernamePanel extends BSPanel
{
	private JLabel usernameLabel;
	
	private JTextField usernameTextField;
	
	private JButton previousButton;
	
	private JButton nextButton;
	
	
	
	private UsernamePanelObserver observer;
	
	
	
	public UsernamePanel(UsernamePanelObserver observer) {
		super(ViewConst.BATTLESHEEP_BACKGROUND, new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* items */
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(Color.WHITE);
		
		usernameTextField = new JTextField();
		
		previousButton = new JButton("Prevoius");
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPrevious();
			}
		});
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNext();
			}
		});
		
		/* this panel */
		
		BSPanel northPanel = new BSPanel(new Color(0, 0, 0, 0), new GridLayout(1, 2, 10, 10));
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(usernameLabel);
		northPanel.add(usernameTextField);
		
		BSPanel southPanel = new BSPanel(new Color(0, 0, 0, 0), new GridLayout(1, 2, 10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(previousButton);
		southPanel.add(nextButton);
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	private void actionPrevious() {
		if (observer != null)
			observer.onUsernamePanelPreviousClick();
	}
	
	private void actionNext() {
		String text = usernameTextField.getText();
		if (text == null || text.isEmpty())
			MessageFactory.informationDialog(this, "Please, fill the username field");
		else if (observer != null)
			observer.onUsernamePanelNextClick(text);
	}
}