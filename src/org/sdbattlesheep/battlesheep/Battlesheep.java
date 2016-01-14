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



package org.sdbattlesheep.battlesheep;



import javax.swing.JOptionPane;

import org.sdbattlesheep.Resources;
import org.sdbattlesheep.battlesheep.communication.PlayerRegistration;
import org.sdbattlesheep.battlesheep.model.player.Me;
import org.sdbattlesheep.battlesheep.model.player.Opponent;
import org.sdbattlesheep.battlesheep.view.AFrame;
import org.sdbattlesheep.battlesheep.view.game.GameFrame;
import org.sdbattlesheep.battlesheep.view.registration.RegistrationFrame;
import org.sdbattlesheep.battlesheep.view.registration.RegistrationFrameObserver;



public class Battlesheep implements RegistrationFrameObserver
{
	private RegistrationFrame registrationFrame;
	
	private GameFrame gameFrame;
	
	
	
	private Me me;
	
	private Opponent[] opponents;
	
	
	
	public Battlesheep() {
		registrationFrame = new RegistrationFrame(
			Resources.FIELD_ROWS,
			Resources.FIELD_COLS,
			Resources.SHEEPS_NUMBER,
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
				JOptionPane.showMessageDialog(null, "Ora dovrebbe partire il gioco!", AFrame.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		t.start();
		
		
	}
}