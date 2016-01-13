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

import game.communication.PlayerRegistration;
import game.model.ModelResources;
import game.model.player.Me;
import game.model.player.Opponent;
import game.view.ViewResources;
import game.view.game.GameFrame;
import game.view.registration.RegistrationFrame;
import game.view.registration.RegistrationFrameObserver;



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
	public void onRegistrationClick(final String username, boolean[][] sheeps) {
		me = new Me(username, sheeps);
		
		Thread t = new Thread(new Runnable() {			
			@Override
			public void run() {
				/*
				 * FIXME calcolare la porta che bilda il client
				 * FIXME se ci sono dei problemi nella join (exception) dobbiamo 
				 * 		 mostrare un messaggio che qualcosa è andato storto,
				 * 		 altrimenti dobbiamo andare nella schermata di gioco!
				 * 		 Ma intanto il la view dovrebbe entrare in una fase di waiting!
				 * 		 Bisogna certamente creare un Observer con almeno 2 metodi: onStart e onError! 
				 */				
				try {
					PlayerRegistration.Join(username, 20000);
				} catch (Exception e) {
					e.printStackTrace();	
				}
				
				System.out.println("registrato!");
				
				// fill "opponents"
				
				// gameFrame = new GameFrame();
				JOptionPane.showMessageDialog(null, "Ora dovrebbe partire il gioco!", ViewResources.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		t.start();
		
		
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