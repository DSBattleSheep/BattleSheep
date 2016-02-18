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



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.registration.observer.SheepsPositionPanelObserver;
import org.sd.battlesheep.view.registration.utils.WhiteButton;
import org.sd.battlesheep.view.registration.utils.WhiteLabel;
import org.sd.battlesheep.view.utils.Cell;
import org.sd.battlesheep.view.utils.Field;
import org.sd.battlesheep.view.utils.FieldObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class SheepsPositionPanel extends APanel implements FieldObserver
{
	private WhiteLabel positionLabel;
	
	private WhiteLabel remainingLabel;
	
	private Field field;
	
	private WhiteButton previousButton;
	
	private WhiteButton registrationButton;
	
	
	
	private int sheeps;
	
	private SheepsPositionPanelObserver observer;
	
	
	
	public SheepsPositionPanel(int rows, int cols, int sheeps, SheepsPositionPanelObserver observer) {
		super(ViewConst.BATTLESHEEP_BACKGROUND);
		
		/* model */
		
		if (sheeps < 1)
			throw new IllegalArgumentException("Sheeps: less than 1");
		if (sheeps > (rows * cols))
			throw new IllegalArgumentException("Sheeps: greater than cells number");
		this.sheeps = sheeps;
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
		/* items */
		
		positionLabel = new WhiteLabel("Sheeps Position:");
		positionLabel.setForeground(Color.WHITE);
		
		remainingLabel = new WhiteLabel(sheeps + "", JLabel.CENTER);
		remainingLabel.setForeground(Color.WHITE);
		
		field = new Field("Dummy", rows, cols, this);
		
		previousButton = new WhiteButton("Prevoius");
		previousButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionPrevious();
			}
		});
		
		registrationButton = new WhiteButton("Registration");
		registrationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionRegistration();
			}
		});
		
		/* this panel */
		
		addNorthPanel(positionLabel, remainingLabel);
		addMiddlePanel(field);
		addSouthPanel(previousButton, registrationButton);
	}
	
	
	
	@Override
	public void onFieldCellClick(String username, Cell source) {
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
		observer.onSheepsPositionPanelPreviousClick();
	}
	
	private void actionRegistration() {
		if (sheeps == 1)
			MessageFactory.informationDialog(this, "Please, add another " + sheeps + " sheep");
		else if (sheeps != 0)
			MessageFactory.informationDialog(this, "Please, add another " + sheeps + " sheeps");
		else
			observer.onSheepsPositionPanelRegistrationClick(field.getSheeps());
	}
}