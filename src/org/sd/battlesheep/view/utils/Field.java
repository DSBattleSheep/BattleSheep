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



package org.sd.battlesheep.view.utils;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sd.battlesheep.view.APanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class Field extends APanel
{
	private JPanel northPanel;
	
	private JLabel usernameLabel;
	
	
	
	private JPanel middlePanel;
	
	private Cell[][] cells;
	
	
	
	private String username;
	
	private int rows;
	
	private int cols;
	
	private FieldObserver observer;
	
	
	
	public Field(String username, int rows, int cols, FieldObserver observer) {
		super(new Color(0, 0, 0, 0), new BorderLayout());
		
		/* model */
		
		this.username = username == null ? "" : username;
		this.rows = rows;
		this.cols = cols;
		this.observer = observer;
		
		/* north panel */
		
		northPanel = new JPanel(new GridBagLayout());
		northPanel.setBackground(new Color(0, 0, 0, 0));
		
		usernameLabel = new JLabel(this.username, JLabel.CENTER);
		
		northPanel.add(
			usernameLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 10, 0),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new JPanel(new GridLayout(rows, cols));
		middlePanel.setBackground(new Color(0, 0, 0, 0));
		
		cells = new Cell[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				cells[r][c] = new Cell(r, c);
				cells[r][c].addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						actionClick((Cell) e.getSource());
					}
				});
				middlePanel.add(cells[r][c]);
			}
		}
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
	}
	
	
	
	private void actionClick(Cell source) {
		if (observer != null)
			observer.onFieldCellClick(source);
	}
	
	
	
	public void setUsername(String username) {
		this.username = username == null ? "" : username;
		usernameLabel.setText(this.username);
	}
	
	public String getUsername() {
		return username;
	}
	
	public JLabel getUsernameLabel() {
		return usernameLabel;
	}
	
	public Cell getCell(int r, int c) {
		return cells[r][c];
	}
	
	
	
	@Override
	public void lock() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c].setEnabled(false);
	}
	
	@Override
	public void unlock() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				cells[r][c].setEnabled(true);
	}
}