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



import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sd.battlesheep.view.BSPanel;
import org.sd.battlesheep.view.game.observer.ListPanelObserver;
import org.sd.battlesheep.view.utils.Field;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class ListPanel extends BSPanel
{
	private JLabel opponentsListLabel;
	
	private JList<Field> fieldsList;
	
	private JScrollPane fieldListScrollPane;
	
	
	
	private Field[] fields;
	
	private ListPanelObserver observer;
	
	
	
	public ListPanel(Field[] fields, ListPanelObserver observer) {
		super(Color.GREEN, new BorderLayout(10, 10));
		
		/* model */
		
		this.fields = fields;
		this.observer = observer;
		
		/* items */
		
		opponentsListLabel = new JLabel("Opponents", JLabel.CENTER);
		
		fieldsList = new JList<Field>(this.fields);
		fieldsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// neither press nor adjust event but release event
				if (!fieldsList.getValueIsAdjusting())
					actionList();
			}
		});
		
		fieldListScrollPane = new JScrollPane(fieldsList);
		
		/* this panel */
		
		add(opponentsListLabel, BorderLayout.NORTH);
		add(fieldListScrollPane, BorderLayout.CENTER);
	}
	
	
	
	private void actionList() {
		if (observer != null) {
			int index = fieldsList.getSelectedIndex();
			observer.onListPanelListValueChanged(index == -1 ? null : fields[index]);
		}
	}
}