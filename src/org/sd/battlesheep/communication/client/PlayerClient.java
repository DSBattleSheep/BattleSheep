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

package org.sd.battlesheep.communication.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.model.KickedOutPlayerException;
import org.sd.battlesheep.model.field.Move;
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.model.player.Opponent;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public class PlayerClient {

	public static List<String> getOrder(String username, int valueRandom, Map<String, NetPlayer> players) {
		
		Map<String, String> orderMap = new TreeMap<String, String>();
		List<String> deleteList = new ArrayList<String>();
		List<String> orderedPlayers = new ArrayList<String>();
		
		
		
		for (String playerName : players.keySet()) {
			NetPlayer player = players.get(playerName);
			
			/**
			 *  tento una connessione al player e chiedo il suo valore random.
			 *  per evitare conflitti di valori abbiamo concatenato il valore e lo username
			 *  per avere una stringa composta che usiamo come chiave nella orderMap.
			 *  La orderMap è una TreeMap e tiene gli elementi in ordine alfabetico!
			 */
			try {
				Registry registry = LocateRegistry.getRegistry(player.getHost(), player.getPort());
				OrderInterface orderInterface = (OrderInterface) registry.lookup(CommunicationConst.GAME_SERVICE_NAME);
				
				System.out.println(player.getHost() + ":" + player.getPort());
				
				orderMap.put(orderInterface.getValueRandom() + playerName, playerName);
				
			} catch (ServerNotActiveException | RemoteException | NotBoundException e) {
				/**
				 *  in caso si Exception è sicuramente colpa della connessione perciò possiamo eliminare il client
				 *  dalla lista dei giocatori 
				 */
				System.out.println("deleteList.add: " + playerName);
				System.out.println(e.getMessage());
				deleteList.add(playerName);
			}
		}
		
		// inserisco me stesso nella mappa
		orderMap.put(valueRandom + username, username);
		
		// elimino i client morti
		for (String playerName : deleteList)
			players.remove(playerName);
		
		// creo la lista di player ordinata
		for (String player : orderMap.keySet()) 
			orderedPlayers.add(orderMap.get(player));
		
		System.out.println(orderedPlayers);
		
		return orderedPlayers;
		
	}
	
	public static Move connectToPlayer(Opponent turnOwner, String myUser, Move oldMove) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException, KickedOutPlayerException {
		//System.out.println("user is:"+ turnOwner.getUsername()+"host is: "turnOwner.getHost()+ "");
		TurnOwnerInterface turnInterface = (TurnOwnerInterface) Naming
				.lookup("rmi://" + turnOwner.getHost() + ":" + turnOwner.getPort() + "/" + CommunicationConst.GAME_SERVICE_NAME);
		return turnInterface.connectToTurnOwner(myUser, oldMove);
	}
	
	public static boolean attackPlayer(Opponent opponent, int x, int y) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException {
		AttackInterface attackInterface = (AttackInterface) Naming
				.lookup("rmi://" + opponent.getHost() + ":" + opponent.getPort() + "/" + CommunicationConst.GAME_SERVICE_NAME);
		return attackInterface.attackPlayer(x, y);
	}
	
	public static void pushMoveUpdate(Opponent opponent, Move newMove) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException {
		PushMoveInterface pushInterface = (PushMoveInterface) Naming
				.lookup("rmi://" + opponent.getHost() + ":" + opponent.getPort() + "/" + CommunicationConst.GAME_SERVICE_NAME);
		pushInterface.pushMoveUpdate(newMove);
	}
	
}
