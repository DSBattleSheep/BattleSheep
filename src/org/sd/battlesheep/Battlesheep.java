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



import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.client.MoveAvailableInterface;
import org.sd.battlesheep.communication.client.PlayerClient;
import org.sd.battlesheep.communication.client.PlayerRegistration;
import org.sd.battlesheep.communication.client.PlayerServer;
import org.sd.battlesheep.model.MaxPortRetryException;
import org.sd.battlesheep.model.ModelConst;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.field.Move;
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.model.player.APlayer;
import org.sd.battlesheep.model.player.Me;
import org.sd.battlesheep.model.player.Opponent;
import org.sd.battlesheep.view.MessageFactory;
import org.sd.battlesheep.view.game.GameFrame;
import org.sd.battlesheep.view.game.GameFrameObserver;
import org.sd.battlesheep.view.registration.RegistrationFrame;
import org.sd.battlesheep.view.registration.RegistrationFrameObserver;



/**
 * Classe principale per il client (gioco).
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Battlesheep implements RegistrationFrameObserver, GameFrameObserver, MoveAvailableInterface
{
	private static Battlesheep instance;
	
	
	private RegistrationFrame registrationFrame;
	
	private GameFrame gameFrame;
	
	
	
	private PlayerServer playerServer;
	
	private Map<String, APlayer> playerMap;

	private ArrayList<String> opponentList;
	
	private Me me;
	
	private List<APlayer> orderList;
	
	

	public static Battlesheep getInstance() {
		return Battlesheep.instance;
	}
	
	public Battlesheep() {
		Battlesheep.instance = this;
		playerMap = new HashMap<String, APlayer>();
		
		registrationFrame = new RegistrationFrame(
			ModelConst.FIELD_ROWS,
			ModelConst.FIELD_COLS,
			ModelConst.SHEEPS_NUMBER,
			this
		);
	}
	
	private class GameFrameSetTurnRunnable implements Runnable {

		String username;
		
		boolean lock;
		
		private GameFrameSetTurnRunnable(String username, boolean lock) {
			this.username = username;
			this.lock = lock;
		}
		
		@Override
		public void run() {
			gameFrame.setTurn(username);
			if (lock)
				gameFrame.lock();
			else
				gameFrame.unlock();
		}		
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
						playerServer = new PlayerServer(Battlesheep.getInstance());
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
				
				orderList = new ArrayList<APlayer>();
				opponentList = new ArrayList<String>();
				
				for(String pUsername : turnList) {
					if(pUsername!=username) {
						NetPlayer currPlayer = players.get(pUsername);
						Opponent opponent = new Opponent(currPlayer, ModelConst.FIELD_ROWS, ModelConst.FIELD_COLS,
								ModelConst.SHEEPS_NUMBER);
						orderList.add(opponent);
						playerMap.put(opponent.getUsername(), opponent);
						opponentList.add(pUsername);
					} else {
						orderList.add(me);
						playerMap.put(me.getUsername(), me);
					}
				}
				
				try {
					SwingUtilities.invokeAndWait(new Runnable() {					
						@Override
						public void run() {
							registrationFrame.dispose();			
							gameFrame = new GameFrame(username, opponentList, ModelConst.FIELD_ROWS, 
									ModelConst.FIELD_COLS, Battlesheep.getInstance());
						}
					});
				} catch (InvocationTargetException | InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
					
				
				System.out.println("gameLoop()!!!");
				gameLoop();
			}
		});
		
		t.start();
	}
	
	
	
	private void gameLoop() {
		boolean ended=false;
		int currPlayerIndex=0;
		APlayer turnOwner;
		
		
		while(!ended) {
			//se è il mio turno assegno il numero di opponent (ovvero mi sblocco)
			turnOwner = orderList.get(currPlayerIndex);
			
			if(turnOwner instanceof Me) {
				playerServer.setPlayerNum(orderList.size());
				
				// TODO: condition await fino alla fine del turno
				
			} else {
				try {
					PlayerClient.connectToPlayer((Opponent) turnOwner, me.getUsername());
				} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//il tizio col turno è morto, ricomincio
					continue;
				}
			}
			currPlayerIndex = (currPlayerIndex + 1) % orderList.size();
			//se non è il mio turno, chiedo la mossa al player che ha il turno
			
		}
	}



	@Override
	public void canMove() {
		SwingUtilities.invokeLater(new GameFrameSetTurnRunnable(me.getUsername(), false));
	}

	@Override
	public void onGameFrameAttack(final String username, final int x, final int y) {
		
		//TODO: invokelater per bloccare la view
		
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					boolean hit = PlayerClient.attackPlayer((Opponent) playerMap.get(username), x, y);
					playerServer.setMove(new Move(username, x, y, hit));
					
					//TODO: invokelater per notificare la view e basta
					
					// TODO: condition unlock perchè è finito il turno!
					
				} catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	



	@Override
	public void onGameFrameExitClick() {
		// TODO Auto-generated method stub
		
	}
}