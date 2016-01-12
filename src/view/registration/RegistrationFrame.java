/*
 * Battlesheep is a funny remake of the famous BattleShip game, developed
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



package view.registration;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.RegistrationObserver;
import view.ViewResources;



@SuppressWarnings("serial")
public class RegistrationFrame extends JFrame
{
	private static final int FRAME_WIDTH = 500;
	
	private static final int FRAME_HEIGHT = 500;
	
	
	
	private UsernamePanel playerNamePanel;
	
	private FieldPanel playerFieldPanel;
	
	private JPanel southPanel;
	
	private JButton leftButton;
	
	private JButton rightButton;
	
	
	
	private RegistrationObserver registrationObserver;
	
	
	
	public RegistrationFrame(RegistrationObserver registrationObserver) {
		super(ViewResources.PROGRAM_NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		playerNamePanel = new UsernamePanel();
		playerFieldPanel = new FieldPanel();
		initSouthPanel();
		
		add(playerNamePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		this.registrationObserver = registrationObserver;
	}
	
	
	
	private void initSouthPanel() {
		southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		southPanel.setBackground(Color.WHITE);
		southPanel.setOpaque(true);
		
		leftButton = new JButton("Exit");
		leftButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (leftButton.getText().equals("Exit"))
					actionExit();
				else if (leftButton.getText().equals("<<"))
					actionPrev();
			}
		});
		
		rightButton = new JButton(">>");
		rightButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (rightButton.getText().equals(">>"))
					actionNext();
				else if (rightButton.getText().equals("Play"))
					actionPlay();
			}
		});
		
		southPanel.add(leftButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		southPanel.add(rightButton, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}
	
	
	
	private void actionExit() {
		dispose();
	}
	
	private void actionNext() {
		rightButton.setEnabled(false);
		remove(playerNamePanel);
		add(playerFieldPanel, BorderLayout.CENTER);
		leftButton.setText("<<");
		rightButton.setText("Play");
		rightButton.setEnabled(true);
		update(getGraphics());
	}
	
	private void actionPrev() {
		leftButton.setEnabled(false);
		remove(playerFieldPanel);
		add(playerNamePanel, BorderLayout.CENTER);
		update(getGraphics());
		leftButton.setText("Exit");
		rightButton.setText(">>");
		leftButton.setEnabled(true);
		update(getGraphics());
	}
	
	private void actionPlay() {
		registrationObserver.notifyRegistration();// TODO
	}
}