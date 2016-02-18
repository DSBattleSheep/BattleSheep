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



package org.sd.battlesheep.view.game.panel;



import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.ViewConst;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class LogPanel extends APanel
{
	private JTextArea logTextArea;
	
	private JScrollPane logScrollPane;
	
	
	
	public LogPanel() {
		super(ViewConst.TRANSPARENT_BACKGROUND);
		
		/* items */
		
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setBackground(Color.BLACK);
		logTextArea.setForeground(Color.GREEN);
		
		((DefaultCaret) logTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		logScrollPane = new JScrollPane(logTextArea);
		
		/* this panel */
		
		addMiddlePanel(logScrollPane);
		//.setPreferredSize(new Dimension(getWidth(), 100));
	}
	
	
	
	public void append(String text) {
		logTextArea.append(text);
	}
}