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



package org.sd.battlesheep.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public abstract class APanel extends JPanel
{
	private Image background;
	
	
	
	public APanel() {
		super(new BorderLayout());
		this.background = null;
	}
	
	public APanel(Color background) {
		super(new BorderLayout());
		setBackground(background);
		this.background = null;
	}
	
	public APanel(Image background) {
		super(new BorderLayout());
		this.background = background;
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		if (background != null)
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		else
			super.paintComponent(g);
	}
	
	
	
	public void addNorthPanel(JComponent component) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		panel.add(component, BorderLayout.CENTER);
		add(panel, BorderLayout.NORTH);
	}
	
	public void addMiddlePanel(JComponent component) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		panel.add(component, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
	}
	
	public void addSouthPanel(JComponent component) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		panel.add(component, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
	
	
	
	public void addNorthPanel(JComponent leftComponent, JComponent rightComponent) {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		panel.add(leftComponent);
		panel.add(rightComponent);
		add(panel, BorderLayout.NORTH);
	}
	
	public void addMiddlePanel(JComponent leftComponent, JComponent rightComponent) {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		panel.add(leftComponent);
		panel.add(rightComponent);
		add(panel, BorderLayout.CENTER);
	}
	
	public void addSouthPanel(JComponent leftComponent, JComponent rightComponent) {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		panel.add(leftComponent);
		panel.add(rightComponent);
		add(panel, BorderLayout.SOUTH);
	}
	
	
	
	public void addMiddlePanel(JComponent[][] components) {
		JPanel panel = new JPanel(new GridLayout(components.length, components[0].length));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.setBackground(ViewConst.TRANSPARENT_BACKGROUND);
		for (int r = 0; r < components.length; r++)
			for (int c = 0; c < components[0].length; c++)
				panel.add(components[r][c]);
		add(panel, BorderLayout.CENTER);
	}
}