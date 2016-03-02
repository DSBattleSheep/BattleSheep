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

package org.sd.battlesheep.communication;



/**
 * Classe per le costanti della comunicazione.
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
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