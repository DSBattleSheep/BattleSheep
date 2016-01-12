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



package model.player;



/**
 * Classe per il giocatore.
 * 
 * Il giocatore è caratterizzato da un username.
 * 
 * @author Giulio Biagini
 */
public abstract class APlayer
{
	/**
	 * l'username del giocatore
	 */
	private String username;
	
	
	
	/**
	 * crea un giocatore con username in input
	 * 
	 * @param username - l'username del giocatore
	 */
	public APlayer(String username) {
		this.username = username;
	}
	
	
	
	/**
	 * funzione che si occupa di verificare se il giocatore ha perso la partita
	 * ovvero se non ha più pecore vive nel proprio campo di gioco
	 * 
	 * @return true se il giocatore ha perso la partita, false altrimenti
	 */
	public abstract boolean lost();
	
	
	
	/**
	 * funzione che si occupa di restituire l'username del giocatore
	 * 
	 * @return l'username del giocatore
	 */
	public String getUsername() {
		return username;
	}
}