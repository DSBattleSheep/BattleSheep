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



package org.sd.battlesheep.view.lobby;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.sd.battlesheep.view.APanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class TablePanel extends APanel
{
	private static final String[] COLUMN_NAMES = {"Username", "Host", "Port"};
	
	
	
	private JPanel northPanel;
	
	private JLabel addressLabel;
	
	
	
	private JPanel middlePanel;
	
	private DefaultTableModel clientsTableModel;
	
	private JTable clientsTable;
	
	private JScrollPane clientsScrollPane;
	
	
	
	private JPanel southPanel;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	public TablePanel(String host, int port) {
		super(Color.WHITE, new BorderLayout());
		
		/* north panel */
		
		northPanel = new JPanel(new GridBagLayout());
		northPanel.setBackground(new Color(0, 0, 0, 0));
		
		addressLabel = new JLabel(host + ":" + port, JLabel.CENTER);
		
		northPanel.add(
			addressLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 5, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new JPanel(new GridBagLayout());
		middlePanel.setBackground(new Color(0, 0, 0, 0));
		
		clientsTableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		
		clientsTable = new JTable(clientsTableModel);
		TableColumnModel clientsTableColumnModel = clientsTable.getColumnModel();
		TableColumn columnUsername = clientsTableColumnModel.getColumn(0);
		TableColumn columnHost = clientsTableColumnModel.getColumn(1);
		TableColumn columnPort = clientsTableColumnModel.getColumn(2);
		Dimension clientsTableSize = clientsTable.getPreferredSize();
		columnUsername.setPreferredWidth(Math.round(clientsTableSize.width * 0.4f));
		columnHost.setPreferredWidth(Math.round(clientsTableSize.width * 0.4f));
		columnPort.setPreferredWidth(Math.round(clientsTableSize.width * 0.2f));
		DefaultTableCellRenderer clientsTableCellRenderer = new DefaultTableCellRenderer();
		clientsTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		columnUsername.setCellRenderer(clientsTableCellRenderer);
		columnHost.setCellRenderer(clientsTableCellRenderer);
		columnPort.setCellRenderer(clientsTableCellRenderer);
		
		clientsScrollPane = new JScrollPane(clientsTable);
		
		middlePanel.add(
			clientsScrollPane,
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
		
		exitButton = new JButton("Exit");
		
		startButton = new JButton("Start");
		
		southPanel.add(
			exitButton,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 10, 5),
				0, 0
			)
		);
		
		southPanel.add(
			startButton,
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
	
	
	
	public DefaultTableModel getClientsTableModel() {
		return clientsTableModel;
	}
	
	public JTable getClientsTable() {
		return clientsTable;
	}
	
	public JButton getExitButton() {
		return exitButton;
	}
	
	public JButton getStartButton() {
		return startButton;
	}
	
	
	
	@Override
	public void lock() {
		clientsTable.setEnabled(false);
		startButton.setEnabled(false);
	}

	@Override
	public void unlock() {
		clientsTable.setEnabled(true);
		startButton.setEnabled(true);
	}
}