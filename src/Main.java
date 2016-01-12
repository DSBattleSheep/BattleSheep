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



import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.ModelResources;
import model.player.Me;
import model.player.Opponent;
import view.ViewResources;
import view.game.GameFrame;
import view.registration.RegistrationFrame;
import view.registration.RegistrationFrameObserver;



public class Main implements RegistrationFrameObserver
{
	private RegistrationFrame registrationFrame;
	
	private GameFrame gameFrame;
	
	
	
	private Me me;
	
	private Opponent[] opponents;
	
	
	
	private Main() {
		registrationFrame = new RegistrationFrame(
			ModelResources.FIELD_ROWS,
			ModelResources.FIELD_COLS,
			ModelResources.SHEEPS_NUMBER,
			this
		);
	}
	
	
	
	@Override
	public void onRegistrationClick(String username, boolean[][] sheeps) {
		me = new Me(username, sheeps);
		
		// communication with the server (in a thread)
		
		// fill "opponents"
		
		// gameFrame = new GameFrame();
		JOptionPane.showMessageDialog(null, "Ora dovrebbe partire il gioco!", ViewResources.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
	public static void main(String[] args) throws IOException {
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