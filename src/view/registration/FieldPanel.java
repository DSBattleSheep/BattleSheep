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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import view.ViewResources;



@SuppressWarnings("serial")
public class FieldPanel extends JPanel
{
	private JPanel northPanel;
	
	private JLabel label;
	
	private JPanel middlePanel;
	
	private JToggleButton[][] field;
	
	
	
	public FieldPanel() {
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
		
		label = new JLabel("Player name:");
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		
		northPanel.add(label, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}
	
	private void initMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridBagLayout());
		middlePanel.setBackground(Color.WHITE);
		middlePanel.setOpaque(true);
		
		field = new JToggleButton[ViewResources.FIELD_ROWS][ViewResources.FIELD_COLS];
		for (int r = 0; r < ViewResources.FIELD_ROWS; r++) {
			for (int c = 0; c < ViewResources.FIELD_COLS; c++) {
				field[r][c] = new JToggleButton(ViewResources.GRASS);
				field[r][c].addActionListener(new ActionListener() {
					@Override public void actionPerformed(ActionEvent e) {
						JToggleButton source = (JToggleButton) e.getSource();
						if (source.isSelected())
							actionSelected(source);
						else
							actionNotSelected(source);
					}
				});
			}
		}
		
		for (int r = 0; r < ViewResources.FIELD_ROWS; r++)
			for (int c = 0; c < ViewResources.FIELD_COLS; c++)
				if (c == 0)
					middlePanel.add(field[r][c], new GridBagConstraints(c, r, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
				else if (c == ViewResources.FIELD_COLS - 1)
					middlePanel.add(field[r][c], new GridBagConstraints(c, r, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
				else
					middlePanel.add(field[r][c], new GridBagConstraints(c, r, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}
	
	
	
	private void actionSelected(JToggleButton source) {
		source.setIcon(ViewResources.SHEEP);
	}
	
	private void actionNotSelected(JToggleButton source) {
		source.setIcon(ViewResources.GRASS);
	}
}