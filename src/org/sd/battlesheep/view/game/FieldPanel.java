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



package org.sd.battlesheep.view.game;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.TransparentPanel;



@SuppressWarnings("serial")
public class FieldPanel extends APanel
{
	/*
	 * constants
	 */
	
	private static final Icon GRASS = new ImageIcon(IMGS_PATH + "grass.png");
	
	private static final Icon SHEEP = new ImageIcon(IMGS_PATH + "sheep.png");
	
	
	
	/*
	 * graphic
	 */
	
	private TransparentPanel northPanel;
	
	private JLabel usernameLabel;
	
	private TransparentPanel middlePanel;
	
	private JToggleButton[][] fieldToggleButtons;
	
	
	
	/*
	 * model
	 */
	
	private int rows;
	
	private int cols;
	
	private String username;
	
	
	
	/*
	 * constructor
	 */
	
	public FieldPanel(int rows, int cols, String username) {
		super(Color.GREEN, new BorderLayout());
		
		/* model */
		
		this.rows = rows;
		this.cols = cols;
		this.username = username;
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
		usernameLabel = new JLabel(username, JLabel.CENTER);
		
		northPanel.add(
			usernameLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
		fieldToggleButtons = new JToggleButton[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				fieldToggleButtons[r][c] = new JToggleButton(GRASS);
				fieldToggleButtons[r][c].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						actionToggleButton((JToggleButton) e.getSource());
					}
				});
			}
		}
		
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				// aggiungo un bottone nella prima colonna
				if (c == 0)
					middlePanel.add(
						fieldToggleButtons[r][c],
						new GridBagConstraints(
							c, r, 1, 1, 1, 1,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 10, 0, 0),
							0, 0
						)
					);
				// aggiungo un bottone nell'ultima colonna
				else if (c == cols - 1)
					middlePanel.add(
						fieldToggleButtons[r][c],
						new GridBagConstraints(
							c, r, 1, 1, 1, 1,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 10),
							0, 0
						)
					);
				// aggiungo un altro bottone
				else
					middlePanel.add(
						fieldToggleButtons[r][c],
						new GridBagConstraints(
							c, r, 1, 1, 1, 1,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0),
							0, 0
						)
					);
	}
	
	
	
	/*
	 * actions
	 */
	
	private void actionToggleButton(JToggleButton source) {
		
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				fieldToggleButtons[r][c].setEnabled(false);
	}
	
	@Override
	public void unlock() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				fieldToggleButtons[r][c].setEnabled(true);
	}
	
	
	
	/*
	 * model
	 */
	
	public String getUsername() {
		return username;
	}
}