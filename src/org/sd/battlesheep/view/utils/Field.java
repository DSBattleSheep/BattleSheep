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



import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class Field extends JPanel
{
	private Cell[][] cells;
	
	
	
	private String username;
	
	private int rows;
	
	private int cols;
	
	private boolean locked;
	
	private FieldObserver observer;
	
	
	
	public Field(String username, int rows, int cols, FieldObserver observer) {
		
		/* model */
		
		this.username = username;
		
		if (rows < 1)
			throw new IllegalArgumentException("Rows: less than 1");
		this.rows = rows;
		
		if (cols < 1)
			throw new IllegalArgumentException("Columns: less than 1");
		this.cols = cols;
		
		this.locked = false;
		
		this.observer = observer;
		
		/* items */
		
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
			}
		}
		
		/* this panel */
		
		setLayout(new GridLayout(rows, cols));
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				add(cells[r][c]);
	}
	
	
	
	private void actionClick(Cell source) {
		if (observer != null && !locked)
			observer.onFieldCellClick(username, source);
	}
	
	
	
	public String getUsername() {
		return username;
	}
	
	public boolean[][] getSheeps() {
		boolean[][] sheeps = new boolean[cols][rows];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				sheeps[c][r] = cells[r][c].isSheep();
		return sheeps;
	}
	
	public void setGrass(int r, int c) {
		cells[r][c].setGrass();
	}
	
	public void setSheep(int r, int c) {
		cells[r][c].setSheep();
	}
	
	public void setHitGrass(int r, int c) {
		cells[r][c].setHitGrass();
	}
	
	public void setHitSheep(int r, int c) {
		cells[r][c].setHitSheep();
	}
	
	
	
	public void lock() {
		locked = true;
	}
	
	public void unlock() {
		locked = false;
	}
	
	
	
	@Override
	public String toString() {
		return username;
	}
}