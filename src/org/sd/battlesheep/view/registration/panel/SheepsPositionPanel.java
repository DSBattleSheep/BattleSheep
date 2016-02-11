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



package org.sd.battlesheep.view.registration.panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.BattlesheepPanel;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.registration.observer.SheepsPositionPanelObserver;
import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class SheepsPositionPanel extends BattlesheepPanel implements FieldObserver
{
	private JLabel positionLabel;
	
	private JLabel remainingLabel;
	
	private Field field;
	
	private JButton previousButton;
	
	private JButton registrationButton;
	
	
	
	private int rows;
	
	private int cols;
	
	private int sheeps;
	
	private SheepsPositionPanelObserver observer;
	
	
	
	public SheepsPositionPanel(int rows, int cols, int sheeps, SheepsPositionPanelObserver observer) {
		super(new BorderLayout());
		
		/* model */
		
		this.rows = rows;
		this.cols = cols;
		this.sheeps = sheeps;
		this.observer = observer;
		
		/* items */
		
		positionLabel = new JLabel("Sheeps Position:");
		positionLabel.setForeground(Color.WHITE);
		
		remainingLabel = new JLabel(sheeps + "");
		remainingLabel.setForeground(Color.WHITE);
		
		field = new Field(rows, cols, this);
		
		previousButton = new JButton("Prevoius");
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPrevious();
			}
		});
		
		registrationButton = new JButton("Registration");
		registrationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionRegistration();
			}
		});
		
		/* this panel */
		
		TransparentPanel northPanel = new TransparentPanel(new BorderLayout(10, 10));
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(positionLabel, BorderLayout.WEST);
		northPanel.add(remainingLabel, BorderLayout.EAST);
		
		TransparentPanel middlePanel = new TransparentPanel(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		middlePanel.add(field, BorderLayout.CENTER);
		
		TransparentPanel southPanel = new TransparentPanel(new GridLayout(1, 2, 10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(previousButton);
		southPanel.add(registrationButton);
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	@Override
	public void onFieldCellClick(Cell source) {
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
	
	
	
	private void actionPrevious() {
		if (observer != null)
			observer.onSheepsPositionPanelPreviousClick();
	}
	
	private void actionRegistration() {
		if (sheeps == 1)
			MessageFactory.informationDialog(this, "Please, add another " + sheeps + " sheep");
		else if (sheeps != 0)
			MessageFactory.informationDialog(this, "Please, add another " + sheeps + " sheeps");
		else if (observer != null) {
			boolean[][] sheepsPosition = new boolean[rows][cols];
			for (int r = 0; r < rows; r++)
				for (int c = 0; c < cols; c++)
					sheepsPosition[r][c] = field.getCell(r, c).isSheep();
			observer.onSheepsPositionPanelRegistrationClick(sheepsPosition);
		}
	}
}