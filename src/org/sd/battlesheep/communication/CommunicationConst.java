package org.sd.battlesheep.communication;



/**
 * Classe per le costanti della comunicazione.
 * 
 * @author Giulio Biagini
 */
public class CommunicationConst
{
	/**
	 * la porta della lobby
	 */
	public static final int LOBBY_PORT = 25099;
	
	
	/**
	 * il nome della stanza della lobby
	 */
	public static final String LOBBY_DEFAULT_ROOM_NAME = "lobby";

	/**
	 * il numero limite di tentativi per cercare una porta libera
	 */
	public static final int MAX_RETRY = 10;

	/**
	 * la porta di default utilizzata dal client per ricevere le connessioni
	 */
	public static final int DEFAULT_PORT = 20000;
	
	/**
	 * il nome del servizio del gioco
	 */
	public static final String GAME_SERVICE_NAME = "gamebattlesheep";
}