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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.TransparentPanel;



/**
 * Classe per il pannello che mostra una tabella con le seguenti informazioni
 * per ogni client connesso:
 * - l'username del giocatore;
 * - l'indirizzo ip dell'host del giocatore;
 * - la porta che l'host del giocatore userà per la comunicazione.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class TablePanel extends APanel
{
	/*
	 * constants
	 */
	
	private static final String[] COLUMN_NAMES = {"Username", "Host", "Port"};
	
	
	
	/*
	 * graphic
	 */
	
	private TransparentPanel middlePanel;
	
	private JTable clientsTable;
	
	private JScrollPane clientsScrollPane;
	
	private TransparentPanel southPanel;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	/*
	 * model
	 */
	
	private DefaultTableModel clientsTableModel;
	
	
	
	/*
	 * constructor
	 */
	
	public TablePanel() {
		super(Color.WHITE, new BorderLayout());
		
		/* model */
		
		clientsTableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		
		/* middle panel */
		
		middlePanel = new TransparentPanel(new GridBagLayout());
		
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
				new Insets(10, 10, 5, 10),
				0, 0
			)
		);
		
		/* south panel */
		
		southPanel = new TransparentPanel(new GridBagLayout());
		
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
		
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	/*
	 * abstract
	 */
	
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
	
	
	
	/*
	 * model
	 */
	
	public int getClientsNumber() {
		return clientsTableModel.getRowCount();
	}
	
	public void addClient(String username, String host, int port) {
		clientsTableModel.addRow(new String[]{username, host, port + ""});
	}
	
	
	
	/*
	 * graphic
	 */
	
	public JButton getExitButton() {
		return exitButton;
	}
	
	public JButton getStartButton() {
		return startButton;
	}
}