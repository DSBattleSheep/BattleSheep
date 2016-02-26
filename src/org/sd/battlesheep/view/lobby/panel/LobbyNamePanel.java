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



package org.sd.battlesheep.view.lobby.panel;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.lobby.observer.LobbyNamePanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LobbyNamePanel  extends APanel
{
	private JLabel lobbyNameLabel;
	
	private JTextField lobbyNameTextField;
	
	private JButton exitButton;
	
	private JButton nextButton;
	
	
	
	private LobbyNamePanelObserver observer;
	
	
	
	public LobbyNamePanel(LobbyNamePanelObserver observer) {
		super(ViewConst.WHITE_BACKGROUND);
		
		/* model */
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* items */
		
		lobbyNameLabel = new JLabel("Lobby Name:");
		
		lobbyNameTextField = new JTextField();
		lobbyNameTextField.addKeyListener(new KeyListener() {
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
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
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
		
		addNorthPanel(lobbyNameLabel, lobbyNameTextField);
		addSouthPanel(exitButton, nextButton);
	}
	
	
	
	private void actionExit() {
		observer.onLobbyNamePanelExitClick();
	}
	
	private void actionNext() {
		String text = lobbyNameTextField.getText();
		if (text == null || text.isEmpty())
			MessageFactory.informationDialog(this, "Please, fill the lobby name field");
		else
			observer.onLobbyNamePanelNextClick(text);
	}
}