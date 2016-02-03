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



import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.sd.battlesheep.view.APanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class Field extends APanel
{
	private Cell[][] cells;
	
	
	
	private int rows;
	
	private int cols;
	
	private FieldObserver observer;
	
	
	
	public Field(int rows, int cols, FieldObserver observer) {
		super(new Color(0, 0, 0, 0), new GridLayout(rows, cols));
		
		/* model */
		
		this.rows = rows;
		this.cols = cols;
		this.observer = observer;
		
		/* this panel */
		
		cells = new Cell[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				cells[r][c] = new Cell();
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
				add(cells[r][c]);
			}
		}
	}
	
	
	
	private void actionClick(Cell source) {
		if (observer != null)
			observer.onFielsCellClick(source);
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