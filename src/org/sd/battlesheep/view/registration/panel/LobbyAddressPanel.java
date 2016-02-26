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
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.registration.observer.LobbyAddressPanelObserver;
import org.sd.battlesheep.view.registration.utils.WhiteButton;
import org.sd.battlesheep.view.registration.utils.WhiteLabel;
import org.sd.battlesheep.view.registration.utils.WhiteList;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LobbyAddressPanel extends APanel
{
	private WhiteLabel addressLabel;
	
	private WhiteList lobbiesList;
	
	private JScrollPane lobbiesScrollPane;
	
	private WhiteButton exitButton;
	
	private WhiteButton nextButton;
	
	
	
	private ArrayList<String> hosts;
	
	private ArrayList<String> names;
	
	private LobbyAddressPanelObserver observer;
	
	
	
	public LobbyAddressPanel(LobbyAddressPanelObserver observer) {
		super(ViewConst.BATTLESHIP_BACKGROUND);
		
		/* model */
		
		hosts = new ArrayList<String>();
		names = new ArrayList<String>();
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* items */
		
		addressLabel = new WhiteLabel("Looking for lobbies...", ViewConst.WAITING_ICON, JLabel.CENTER);
		addressLabel.setForeground(Color.WHITE);
		
		lobbiesList = new WhiteList();
		
		lobbiesScrollPane = new JScrollPane(lobbiesList);
		lobbiesScrollPane.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		
		exitButton = new WhiteButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExit();
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
		
		addNorthPanel(addressLabel);
		addMiddlePanel(lobbiesScrollPane);
		addSouthPanel(exitButton, nextButton);
	}
	
	
	
	private void actionExit() {
		observer.onLobbyAddressPanelExitClick();
	}
	
	private void actionNext() {
		int index = lobbiesList.getSelectedIndex();
		if (index == -1)
			MessageFactory.informationDialog(this, "Please, select a lobby address");
		else
			observer.onLobbyAddressPanelNextClick(hosts.get(index));
	}
	
	
	
	public void addLobby(String host, String name) {
		if (host == null)
			throw new IllegalArgumentException("Host: null string");
		if (host.isEmpty())
			throw new IllegalArgumentException("Host: empty string");
		if (name == null)
			throw new IllegalArgumentException("Name: null string");
		if (name.isEmpty())
			throw new IllegalArgumentException("Name: empty string");
		hosts.add(host);
		names.add(name);
		String[] array = new String[hosts.size()];
		for (int i = 0; i < array.length; i++)
			array[i] = names.get(i) + "@" + hosts.get(i);
		lobbiesList.setListData(array);
	}
	
	public void discoveryFinished() {
		addressLabel.setText("Lobbies:");
		addressLabel.setIcon(null);
		revalidate();
		repaint();
	}
}