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



package org.sd.battlesheep;



import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.sd.battlesheep.view.game.GameFrame;
import org.sd.battlesheep.view.main.MainFrame;
import org.sd.battlesheep.view.main.MainFrameObserver;




/**
 * @author Giulio Biagini
 */
public class Main implements MainFrameObserver
{
	private MainFrame mainFrame;
	
	
	
	private Main() {
		mainFrame = new MainFrame(this);
		String myName = "Biagio";
		ArrayList<String> opponentsName = new ArrayList<>();
		opponentsName.add("Ise");
		opponentsName.add("Minizinc");
		opponentsName.add("Bera");
		opponentsName.add("Danger");
		// new GameFrame(myName, opponentsName, 10, 10, null);
	}
	
	
	
	@Override
	public void onMainFrameExitClick() {
		mainFrame.dispose();
	}
	
	@Override
	public void onMainFrameStartClick(boolean serverMode) {
		mainFrame.dispose();
		if (serverMode) 
			new Lobby();
		else
			new Battlesheep();
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			
		} catch (InstantiationException ex) {
			
		} catch (IllegalAccessException ex) {
			
		} catch (UnsupportedLookAndFeelException ex) {
			
		}
		
		new Main();
	}
}