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
public class FieldsListPanel extends APanel
{
	private JLabel opponentsLabel;
	
	private JList<Field> fieldsList;
	
	private JScrollPane fieldsListScrollPane;
	
	
	
	private ArrayList<Field> fields;
	
	private FieldsListObserver observer;
	
	
	
	public FieldsListPanel(String[] usernames, int rows, int cols, FieldsListObserver observer) {
		super(ViewConst.TRANSPARENT_BACKGROUND);
		
		/* model */
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		if (usernames == null)
			throw new IllegalArgumentException("Usernames: null array");
		if (usernames.length < 1)
			throw new IllegalArgumentException("Usernames: less than 1");
		fields = new ArrayList<>();
		for (int i = 0; i < usernames.length; i++) {
			if (usernames[i] == null)
				throw new IllegalArgumentException("Username " + (i + 1) + ": null string");
			if (usernames[i].isEmpty())
				throw new IllegalArgumentException("Username " + (i + 1) + ": empty string");
			fields.add(new Field(usernames[i], rows, cols, null));
		}
		
		/* items */
		
		opponentsLabel = new JLabel("Opponents", JLabel.CENTER);
		
		fieldsList = new JList<Field>(fields.toArray(new Field[fields.size()]));
		fieldsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// neither press nor adjust event but release event
				if (!fieldsList.getValueIsAdjusting())
					actionList();
			}
		});
		
		fieldsListScrollPane = new JScrollPane(fieldsList);
		
		/* this panel */
		
		addNorthPanel(opponentsLabel);
		addMiddlePanel(fieldsListScrollPane);
	}
	
	
	
	private void actionList() {
		try {
			observer.onListValueChanged(fields.get(fieldsList.getSelectedIndex()));
		} catch (ArrayIndexOutOfBoundsException ex){
			
		}
	}
	
	
	
	public void selectField(String username) {
		for (int i = 0; i < fields.size(); i++)
			if (fields.get(i).getUsername().equals(username)) {
				fieldsList.setSelectedIndex(i);
				actionList();
				break;
			}
	}
	
	public void removeField(String username) {
		for (Field field : fields)
			if (field.getUsername().equals(username)) {
				fields.remove(field);
				break;
			}
		fieldsList.setListData(fields.toArray(new Field[fields.size()]));
		fieldsList.setSelectedIndex(0);
		actionList();
	}
}