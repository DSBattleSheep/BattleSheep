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



package org.sd.battlesheep.view.registration;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class SheepsPositionPanel extends ASheepPanel implements FieldObserver
{
	private JPanel northPanel;
	
	private JLabel positionLabel;
	
	private JLabel remainingLabel;
	
	
	
	private JPanel middlePanel;
	
	private Field field;
	
	
	
	private JPanel southPanel;
	
	private JButton previousButton;
	
	private JButton registrationButton;
	
	
	
	private int rows;
	
	private int cols;
	
	private int sheeps;
	
	
	
	public SheepsPositionPanel(int rows, int cols, int sheeps) {
		super(new BorderLayout());
		
		/* model */
		
		this.rows = rows;
		this.cols = cols;
		this.sheeps = sheeps;
		
		/* north panel */
		
		northPanel = new JPanel(new GridBagLayout());
		northPanel.setBackground(new Color(0, 0, 0, 0));
		
		positionLabel = new JLabel("Sheeps Position:");
		positionLabel.setForeground(Color.WHITE);
		
		remainingLabel = new JLabel(sheeps + "");
		remainingLabel.setForeground(Color.WHITE);
		
		northPanel.add(
			positionLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 0.9, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 5),
				0, 0
			)
		);
		northPanel.add(
			remainingLabel,
			new GridBagConstraints(
				1, 0, 1, 1, 0.1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 5, 10, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new JPanel(new GridBagLayout());
		middlePanel.setBackground(new Color(0, 0, 0, 0));
		
		field = new Field(rows, cols, this);
		
		middlePanel.add(
			field,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 10),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new JPanel(new GridBagLayout());
		southPanel.setBackground(new Color(0, 0, 0, 0));
		
		previousButton = new JButton("Prevoius");
		
		registrationButton = new JButton("Registration");
		
		southPanel.add(
			previousButton,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 5),
				0, 0
			)
		);
		southPanel.add(
			registrationButton,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	@Override
	public void onFielsCellClick(Cell source) {
		if (source.isGrass() && sheeps > 0) {
			source.setSheep();
			sheeps--;
			remainingLabel.setText(sheeps + "");
			SwingUtilities.updateComponentTreeUI(this);
		} else if (source.isSheep()) {
			source.setGrass();
			sheeps++;
			remainingLabel.setText(sheeps + "");
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	@Override
	public void onFieldClick() {
		
	}
	
	
	
	public int getRemainingSheeps() {
		return sheeps;
	}
	
	public boolean[][] getSheepsPosition() {
		boolean[][] positions = new boolean[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				positions[r][c] = field.getCell(r, c).isSheep();
		return positions;
	}
	
	
	
	public JButton getPreviousButton() {
		return previousButton;
	}
	
	public JButton getRegistrationButton() {
		return registrationButton;
	}
	
	
	
	@Override
	public void lock() {
		field.lock();
		registrationButton.setEnabled(false);
	}
	
	@Override
	public void unlock() {
		field.unlock();
		registrationButton.setEnabled(true);
	}
}