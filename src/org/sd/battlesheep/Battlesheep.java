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



import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.client.PlayerClient;
import org.sd.battlesheep.communication.client.PlayerRegistration;
import org.sd.battlesheep.communication.client.PlayerServer;
import org.sd.battlesheep.model.MaxPortRetryException;
import org.sd.battlesheep.model.ModelConst;
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.model.player.Me;
import org.sd.battlesheep.model.player.MeFactory;
import org.sd.battlesheep.model.player.Opponent;
import org.sd.battlesheep.view.AFrame;
import org.sd.battlesheep.view.game.GameFrame;
import org.sd.battlesheep.view.registration.RegistrationFrame;
import org.sd.battlesheep.view.registration.RegistrationFrameObserver;



/**
 * Classe principale per il client (gioco).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Battlesheep implements RegistrationFrameObserver
{
	private RegistrationFrame registrationFrame;
	
	private GameFrame gameFrame;
	
	
	
	private Me me;
	
	private Opponent[] opponents;
	
	
	
	public Battlesheep() {
		registrationFrame = new RegistrationFrame(
			ModelConst.FIELD_ROWS,
			ModelConst.FIELD_COLS,
			ModelConst.SHEEPS_NUMBER,
			this
		);
	}
	
	
	
	@Override
	public void onRegistrationFrameExitClick() {
		registrationFrame.dispose();
	}
	
	@Override
	public void onRegistrationFrameOkClick(final String username, final boolean[][] sheeps) {
		 
		
		Thread t = new Thread(new Runnable() {			
			@Override
			public void run() {
				/*
				 * FIXME se ci sono dei problemi nella join (exception) dobbiamo 
				 * 		 mostrare un messaggio che qualcosa è andato storto,
				 * 		 altrimenti dobbiamo andare nella schermata di gioco!
				 * 		 Ma intanto il la view dovrebbe entrare in una fase di waiting!
				 * 		 Bisogna certamente creare un Observer con almeno 2 metodi: onStart e onError! 
				 */	
				
				PlayerServer playersServer = null;
								
				Map<String, NetPlayer> players = null;
				
				try {
					playersServer = new PlayerServer();
					me = MeFactory.NewMe(username, sheeps, playersServer);
					playersServer.setMe(me);
					players = PlayerRegistration.Join(username, me.getPort());
				} catch (MaxPortRetryException | MalformedURLException e) {
					JOptionPane.showMessageDialog(null, "MaxPortRetryException", AFrame.PROGRAM_NAME, JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				} catch (RemoteException | ServerNotActiveException | NotBoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Eccezione", AFrame.PROGRAM_NAME, JOptionPane.ERROR_MESSAGE);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							registrationFrame.unlockGui();
						}
					});
					return;
				}
				
				// Abbiamo joinato e la lobby ci ha restituito tutti i player
				
				List<String> turnList = PlayerClient.getOrder(username, playersServer.getValueRandom(), players);
				
				System.out.println("registrato!");
				
				// fill "opponents"
				
				// gameFrame = new GameFrame();
			}
		});
		t.start();
	}
}