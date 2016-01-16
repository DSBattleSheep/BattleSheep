package org.sd.battlesheep.communication.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.model.lobby.NetPlayer;

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
			 *  La orderMap è una TreeMap e tiene gli elementi in ordine alfabetico! ( FIXME : ne siamo sicuri? )
			 */
			try {
				OrderInterface orderInterface = (OrderInterface) Naming
						.lookup("rmi://" + player.getHost() + ":" + player.getPort() + "/" + CommunicationConst.GAME_SERVICE_NAME);
				
				orderMap.put(orderInterface.getValueRandom() + playerName, player.getUsername());
				
			} catch (MalformedURLException | ServerNotActiveException | RemoteException | NotBoundException e) {
				/**
				 *  in caso si Exception è sicuramente colpa della connessione perciò possiamo eliminare il client
				 *  dalla lista dei giocatori 
				 */
				System.out.println("deleteList.add: " + playerName);
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
	
}
