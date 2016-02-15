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



import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;
import org.sd.battlesheep.view.lobby.observer.ClientsPanelObserver;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class ClientsPanel extends APanel
{
	private static final String[] COLUMN_NAMES = {"Username", "Host", "Port"};
	
	
	
	private JLabel addressLabel;
	
	private DefaultTableModel clientsTableModel;
	
	private JTable clientsTable;
	
	private JScrollPane clientsScrollPane;
	
	private JButton exitButton;
	
	private JButton startButton;
	
	
	
	private ClientsPanelObserver observer;
	
	
	
	public ClientsPanel(String host, int port, ClientsPanelObserver observer) {
		super(ViewConst.WHITE_BACKGROUND);
		
		/* model */
		
		if (host == null)
			throw new IllegalArgumentException("Host: null string");
		if (host.isEmpty())
			throw new IllegalArgumentException("Host: empty string");
		
		if (port < 0)
			throw new IllegalArgumentException("Port: less than 0");
		if (port > 65535)
			throw new IllegalArgumentException("Port: greater than 65535");
		
		if (observer == null)
			throw new IllegalArgumentException("Observer: null object");
		this.observer = observer;
		
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
				actionExit();
			}
		});
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionStart();
			}
		});
		
		/* this panel */
		
		addNorthPanel(addressLabel);
		addMiddlePanel(clientsScrollPane);
		addSouthPanel(exitButton, startButton);
	}
	
	
	
	private void actionExit() {
		observer.onClientsPanelExitClick();
	}
	
	private void actionStart() {
		observer.onClientsPanelStartClick();
	}
	
	
	
	public int getClientsNumber() {
		return clientsTableModel.getRowCount();
	}
	
	public void addClient(String username, String host, int port) {
		if (username == null)
			throw new IllegalArgumentException("Username: null string");
		if (username.isEmpty())
			throw new IllegalArgumentException("Username: empty string");
		if (host == null)
			throw new IllegalArgumentException("Host: null string");
		if (host.isEmpty())
			throw new IllegalArgumentException("Host: empty string");
		if (port < 0)
			throw new IllegalArgumentException("Port: less than 0");
		if (port > 65535)
			throw new IllegalArgumentException("Port: greater than 65535");
		clientsTableModel.addRow(new String[]{username, host, port + ""});
	}
}