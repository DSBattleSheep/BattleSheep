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
	private String username;
	
	private int width;
	
	private int height;
	
	private Cell[][] cells;
	
	private boolean locked;
	
	private FieldObserver observer;
	
	
	
	public Field(String username, int width, int height, FieldObserver observer) {
		
		/* model */
		
		if (username == null)
			throw new IllegalArgumentException("Username: null string");
		if (username.isEmpty())
			throw new IllegalArgumentException("Username: empty string");
		this.username = username;
		
		if (width < 1)
			throw new IllegalArgumentException("Width: less than 1");
		this.width = width;
		
		if (height < 1)
			throw new IllegalArgumentException("Height: less than 1");
		this.height = height;
		
		this.locked = false;
		
		this.observer = observer;
		
		/* items */
		
		cells = new Cell[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cells[x][y] = new Cell(x, y);
				cells[x][y].addMouseListener(new MouseListener() {
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
		
		setLayout(new GridLayout(width, height));
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				add(cells[x][y]);
	}
	
	public Field(String username, boolean[][] sheepsPosition, FieldObserver observer) {
		
		/* model */
		
		if (username == null)
			throw new IllegalArgumentException("Username: null string");
		if (username.isEmpty())
			throw new IllegalArgumentException("Username: empty string");
		this.username = username;
		
		if (sheepsPosition == null)
			throw new IllegalArgumentException("Sheeps position: null matrix");
		if (sheepsPosition.length < 1)
			throw new IllegalArgumentException("Sheeps position: less than 1");
		if (sheepsPosition[0].length < 1)
			throw new IllegalArgumentException("Sheeps position: less than 1");
		this.width = sheepsPosition.length;
		this.height = sheepsPosition[0].length;
		
		this.locked = false;
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* items */
		
		cells = new Cell[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cells[x][y] = new Cell(x, y);
				if (sheepsPosition[x][y])
					cells[x][y].setSheep();
				cells[x][y].addMouseListener(new MouseListener() {
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
		
		setLayout(new GridLayout(width, height));
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				add(cells[x][y]);
	}
	
	
	
	private void actionClick(Cell source) {
		if (observer != null && !locked)
			observer.onFieldCellClick(username, source);
	}
	
	
	
	public String getUsername() {
		return username;
	}
	
	public int getCols() {
		return width;
	}
	
	public int getRows() {
		return height;
	}
	
	public boolean[][] getSheepsPosition() {
		boolean[][] sheeps = new boolean[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				sheeps[x][y] = cells[x][y].isSheep();
		return sheeps;
	}
	
	public void setHitGrass(int x, int y) {
		cells[x][y].setHitGrass();
	}
	
	public void setHitSheep(int x, int y) {
		cells[x][y].setHitSheep();
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