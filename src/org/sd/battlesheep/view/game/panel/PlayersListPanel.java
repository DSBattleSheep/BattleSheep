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



package org.sd.battlesheep.view.game.panel;



import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.game.observer.FieldsListObserver;
import org.sd.battlesheep.view.utils.Field;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class PlayersListPanel extends APanel
{
	private JLabel opponentsLabel;
	
	private JList<String> usernamesList;
	
	private JScrollPane usernamesListScrollPane;
	
	
	
	private FieldsListObserver observer;
	
	
	
	public PlayersListPanel(String[] usernames, FieldsListObserver observer) {
		super(ViewConst.TRANSPARENT_BACKGROUND);
		
		/* model */
		
		if (usernames == null)
			throw new IllegalArgumentException("Usernames: null array");
		if (usernames.length < 1)
			throw new IllegalArgumentException("Usernames: less than 1");
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* items */
		
		opponentsLabel = new JLabel("Opponents", JLabel.CENTER);
		
		usernamesList = new JList<String>(usernames);
		usernamesList.setSelectedIndex(0);
		usernamesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// neither press nor adjust event but release event
				if (!usernamesList.getValueIsAdjusting())
					actionList();
			}
		});
		
		usernamesListScrollPane = new JScrollPane(usernamesList);
		
		/* this panel */
		
		addNorthPanel(opponentsLabel);
		addMiddlePanel(usernamesListScrollPane);
	}
	
	
	
	private void actionList() {
		int fieldIndex = usernamesList.getSelectedIndex();
		if (fieldIndex > -1)
			observer.onListValueChanged(fieldIndex);
	}
	
	
	
	public void selectUsername(int fieldIndex) {
		usernamesList.setSelectedIndex(fieldIndex);
		observer.onListValueChanged(fieldIndex);
	}
	
	public void replaceUsernames(ArrayList<Field> fields) {
		if (fields == null)
			throw new IllegalArgumentException("Fields: null array");
		String[] usernames = new String[fields.size()];
		for (int i = 0; i < fields.size(); i++)
			usernames[i] = fields.get(i).getUsername();
		usernamesList.setListData(usernames);
		if (fields.size() != 0) {
			usernamesList.setSelectedIndex(0);
			observer.onListValueChanged(0);
		}
	}
}