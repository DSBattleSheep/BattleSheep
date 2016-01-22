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
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.client.PlayerClient;
import org.sd.battlesheep.communication.client.PlayerRegistration;
import org.sd.battlesheep.communication.client.PlayerServer;
import org.sd.battlesheep.model.MaxPortRetryException;
import org.sd.battlesheep.model.ModelConst;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.model.player.APlayer;
import org.sd.battlesheep.model.player.Me;
import org.sd.battlesheep.model.player.Opponent;
import org.sd.battlesheep.view.MessageFactory;
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
	
	
	
	private PlayerServer playerServer;
	
	private Me me;
	
	private List<APlayer> playerList;
	
	
	
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
		// registrationFrame.dispose();//
		// TODO -> gestire l'uscita dal programma (client)
		System.exit(0);
	}
	
	@Override
	public void onRegistrationFrameRegistrationClick(final String lobbyAddress, final String username, final boolean[][] sheepsPosition) {
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
				
								
				Map<String, NetPlayer> players = null;
				
				System.out.println("Console di: " + username);
				
				try {
					me = new Me(username, sheepsPosition);
					if (playerServer == null)
						playerServer = new PlayerServer();
					playerServer.setMe(me);
					players = PlayerRegistration.Join(username, playerServer.getPort());
					
				} catch (UsernameAlreadyTakenException e) {
					MessageFactory.errorDialog(null, e.getMessage());
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							registrationFrame.unlock();
						}
					});
					return;
				} catch (MaxPortRetryException e) {
					MessageFactory.errorDialog(null, e.getMessage());
					System.exit(1);
				} catch (NotBoundException | ServerNotActiveException | ConnectException | UnmarshalException | MalformedURLException e) {
					MessageFactory.errorDialog(null, e.getMessage());
					return;
				} catch (RemoteException e) {
					e.printStackTrace();					
					return;
				}
			
				
				// Abbiamo joinato e la lobby ci ha restituito tutti i player
				
				List<String> turnList = PlayerClient.getOrder(username, playerServer.getValueRandom(), players);
				
				System.out.println("registrato!");
				
				playerList = new ArrayList<APlayer>();
				
				for(String pUsername : turnList) {
					if(pUsername!=username) {
						NetPlayer currPlayer = players.get(pUsername);
						playerList.add(new Opponent(currPlayer, ModelConst.FIELD_ROWS, ModelConst.FIELD_COLS,
													ModelConst.SHEEPS_NUMBER));
					} else {
						playerList.add(me);
					}
				}
				// fill "opponents"
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						registrationFrame.dispose();
						// gameFrame = new GameFrame(username, turnList, null);
						JOptionPane.showMessageDialog(null, "Parte il giuoco");
					}
				});
				
				//gameLoop();
			}
		});
		
		t.start();
	}
	
	
	
	private void gameLoop() {
		boolean ended=false;
		int currPlayerIndex=0;
		while(!ended) {
			//se è il mio turno assegno il numero di opponent (ovvero mi sblocco)
			if(playerList.get(currPlayerIndex) instanceof Me) {
				playerServer.setPlayerNum(playerList.size());
			} else {
				try {
					PlayerClient.connectToPlayer((Opponent) playerList.get(currPlayerIndex), me.getUsername());
				} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//il tizio col turno è morto, ricomincio
					continue;
				}
			}
			currPlayerIndex = (currPlayerIndex + 1) % playerList.size();
			//se non è il mio turno, chiedo la mossa al player che ha il turno
			
		}
	}
}