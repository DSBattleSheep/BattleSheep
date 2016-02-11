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



package org.sd.battlesheep.view.main;



import java.awt.BorderLayout;

import org.sd.battlesheep.view.BSFrame;
import org.sd.battlesheep.view.main.observer.MainPanelObserver;
import org.sd.battlesheep.view.main.panel.MainPanel;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class MainFrame extends BSFrame implements MainPanelObserver
{
	private MainPanel mainPanel;
	
	
	
	private MainFrameObserver observer;
	
	
	
	public MainFrame(MainFrameObserver observer) {
		super(new BorderLayout());
		
		/* model */
		
		this.observer = observer;
		
		/* panels */
		
		mainPanel = new MainPanel(this);
		
		/* this frame */
		
		add(mainPanel, BorderLayout.CENTER);
		
		pack();
		
		setVisible(true);
	}
	
	
	
	@Override
	public void onMainPanelExitClick() {
		if (observer != null)
			observer.onMainFrameExitClick();
	}
	
	@Override
	public void onMainPanelStartClick(boolean isServerSelected) {
		if (observer != null)
			observer.onMainFrameStartClick(isServerSelected);
	}
}