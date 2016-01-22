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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.TransparentPanel;



/**
 * Classe per il pannello che richiede al giocatore di inserire la posizione
 * delle proprie pecore nel campo di gioco.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class SheepsPositionPanel extends APanel
{
	/*
	 * constants
	 */
	
	private static final Image BATTLESHEEP = new ImageIcon(IMGS_PATH + "battlesheep.jpg").getImage();
	
	private static final Icon GRASS = new ImageIcon(IMGS_PATH + "grass.png");
	
	private static final Icon SHEEP = new ImageIcon(IMGS_PATH + "sheep.png");
	
	
	
	/*
	 * graphic
	 */
	
	private TransparentPanel northPanel;
	
	private JLabel positionLabel;
	
	private JLabel remainingLabel;
	
	private TransparentPanel middlePanel;
	
	private JToggleButton[][] positionsToggleButtons;
	
	private TransparentPanel southPanel;
	
	private JButton previousButton;
	
	private JButton registrationButton;
	
	
	
	/*
	 * model
	 */
	
	private int rows;
	
	private int cols;
	
	private int sheeps;
	
	
	
	/*
	 * constructor
	 */
	
	public SheepsPositionPanel(int rows, int cols, int sheeps, int width, int height) {
		super(BATTLESHEEP, width, height, new BorderLayout());
		
		/* model */
		
		this.rows = rows;
		this.cols = cols;
		this.sheeps = sheeps;
		
		/* north panel */
		
		northPanel = new TransparentPanel(new GridBagLayout());
		
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
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
		positionsToggleButtons = new JToggleButton[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				positionsToggleButtons[r][c] = new JToggleButton(GRASS);
				positionsToggleButtons[r][c].addActionListener(new ActionListener() {
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
						positionsToggleButtons[r][c],
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
						positionsToggleButtons[r][c],
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
						positionsToggleButtons[r][c],
						new GridBagConstraints(
							c, r, 1, 1, 1, 1,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0),
							0, 0
						)
					);
		
		/* south panel */
		
		southPanel = new TransparentPanel(new GridBagLayout());
		
		previousButton = new JButton("Prevoius");
		
		registrationButton = new JButton("Registration");
		
		southPanel.add(
			previousButton,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 10, 5),
				0, 0
			)
		);
		southPanel.add(
			registrationButton,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 5, 10, 10),
				0, 0
			)
		);
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	/*
	 * actions
	 */
	
	private void actionToggleButton(JToggleButton source) {
		// TODO -> sparisce una riga o una colonna per colpa della dimensione che scende a 0
		// voglio aggiungere una pecora ma ho già raggiunto il numero massimo di pecore
		if (source.getIcon().equals(GRASS) && sheeps == 0)
			return;
		// voglio aggiungere una pecora e non ho ancora raggiunto il numero massimo di pecore
		if (source.getIcon().equals(GRASS)) {
			source.setIcon(SHEEP);
			source.setSelected(true);
			sheeps--;
		// voglio togliere una pecora
		} else {
			source.setIcon(GRASS);
			source.setSelected(false);
			sheeps++;
		}
		remainingLabel.setText(sheeps + "");
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	
	/*
	 * abstract
	 */
	
	@Override
	public void lock() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				positionsToggleButtons[r][c].setEnabled(false);
		registrationButton.setEnabled(false);
	}
	
	@Override
	public void unlock() {
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				positionsToggleButtons[r][c].setEnabled(true);
		registrationButton.setEnabled(true);
	}
	
	
	
	/*
	 * model
	 */
	
	public int getRemainingSheeps() {
		return sheeps;
	}
	
	public boolean[][] getPositions() {
		boolean[][] positions = new boolean[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				positions[r][c] = positionsToggleButtons[r][c].isSelected();
		return positions;
	}
	
	
	
	/*
	 * graphic
	 */
	
	public JButton getPreviousButton() {
		return previousButton;
	}
	
	public JButton getRegistrationButton() {
		return registrationButton;
	}
}