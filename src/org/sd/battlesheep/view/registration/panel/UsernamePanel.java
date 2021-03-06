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



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.registration.observer.UsernamePanelObserver;
import org.sd.battlesheep.view.registration.utils.WhiteButton;
import org.sd.battlesheep.view.registration.utils.WhiteLabel;
import org.sd.battlesheep.view.registration.utils.WhiteTextField;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class UsernamePanel extends APanel
{
	private WhiteLabel usernameLabel;
	
	private WhiteTextField usernameTextField;
	
	private WhiteButton previousButton;
	
	private WhiteButton nextButton;
	
	
	
	private UsernamePanelObserver observer;
	
	
	
	public UsernamePanel(UsernamePanelObserver observer) {
		super(ViewConst.BATTLESHEEP_BACKGROUND);
		
		/* model */
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* items */
		
		usernameLabel = new WhiteLabel("Username:");
		usernameLabel.setForeground(Color.WHITE);
		
		usernameTextField = new WhiteTextField();
		usernameTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					actionNext();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		previousButton = new WhiteButton("Prevoius");
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPrevious();
			}
		});
		
		nextButton = new WhiteButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNext();
			}
		});
		
		/* this panel */
		
		addNorthPanel(usernameLabel, usernameTextField);
		addSouthPanel(previousButton, nextButton);
	}
	
	
	
	private void actionPrevious() {
		observer.onUsernamePanelPreviousClick();
	}
	
	private void actionNext() {
		String text = usernameTextField.getText();
		if (text == null || text.isEmpty())
			MessageFactory.informationDialog(this, "Please, fill the username field");
		else
			observer.onUsernamePanelNextClick(text);
	}
}