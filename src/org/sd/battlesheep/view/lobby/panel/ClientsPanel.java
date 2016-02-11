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



package org.sd.battlesheep.view.lobby.panel;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.sd.battlesheep.view.TransparentPanel;
import org.sd.battlesheep.view.WhitePanel;
import org.sd.battlesheep.view.lobby.observer.ClientsPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class ClientsPanel extends WhitePanel
{
	private static final String[] COLUMN_NAMES = {"Username", "Host", "Port"};
	
	
	
	private JLabel addressLabel;
	
	private DefaultTableModel clientsTableModel;
	
	private JTable clientsTable;
	
	private JScrollPane clientsScrollPane;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	public ClientsPanel(String host, int port, final ClientsPanelObserver observer) {
		super(new BorderLayout());
		
		/* items */
		
		addressLabel = new JLabel(host + ":" + port, JLabel.CENTER);
		
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
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (observer != null)
					observer.onClientsPanelExitClick();
			}
		});
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (observer != null)
					observer.onClientsPanelStartClick();
			}
		});
		
		/* this panel */
		
		TransparentPanel northPanel = new TransparentPanel(new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		northPanel.add(addressLabel, BorderLayout.CENTER);
		
		TransparentPanel middlePanel = new TransparentPanel(new BorderLayout());
		middlePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		middlePanel.add(clientsScrollPane, BorderLayout.CENTER);
		
		TransparentPanel southPanel = new TransparentPanel(new GridLayout(1, 2, 10, 10));
		southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		southPanel.add(exitButton);
		southPanel.add(startButton);
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	
	
	public int getClientsNumber() {
		return clientsTableModel.getRowCount();
	}
	
	public void addClient(String username, String host, int port) {
		clientsTableModel.addRow(new String[]{username, host, port + ""});
	}
}