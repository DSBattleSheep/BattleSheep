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



package org.sd.battlesheep.model.player;


import org.sd.battlesheep.model.field.MyField;



/**
 * Classe per il giocatore.
 * 
 * Il giocatore è costituito da un username ed una porta sulla quale è in
 * ascolto in attesa di una connessione da parte di altri giocatori per la
 * comunicazione. Il giocatore ha poi un proprio campo di gioco nel quale è
 * memorizzata la posizione dell'erba, delle pecore e le celle colpite (con
 * successo o meno) dagli avversari.
 * 
 * @author Giulio Biagini, Michele Corazza, Gianluca Iselli
 */
public class Me extends APlayer
{
	/**
	 * il campo di gioco del giocatore
	 */
	private MyField myField;
	
	
	
	/**
	 * crea un giocatore dato un username, le dimensioni del campo di gioco e
	 * la posizione iniziale delle pecore
	 * 
	 * @param username - l'username del giocatore
	 * @param port - la porta per la comunicazione con gli altri giocatori
	 * @param sheeps - la poszione iniziale delle pecore nel campo di gioco
	 */
	public Me(String username, boolean[][] sheeps) {
		super(username);
		this.myField = new MyField(sheeps);
	}
	
	
	
	/**
	 * funzione che si occupa di verificare se il giocatore ha perso la partita
	 * ovvero se non ha più pecore vive nel proprio campo di gioco
	 * 
	 * @return true se il giocatore ha perso la partita, false altrimenti
	 */
	@Override
	public boolean lost() {
		return myField.getAliveSheepsNumber() == 0;
	}
	
	
	
	/**
	 * funzione che si occupa di settare una cella come colpita
	 * 
	 * @param x - coordinata x della mappa
	 * @param y - coordinata y della mappa
	 */
	public void hit(int x, int y) {
		myField.hit(x, y);
	}
	
	
	
	/**
	 * funzione che si occupa di verificare se nella cella indicata
	 * c'è una pecora oppure no
	 * 
	 * @param x - cordinata x della mappa
	 * @param y - cordinata y della mappa
	 * @return true se c'è una pecora nella cella indicata, false altrimenti
	 */
	public boolean isSheep(int x, int y) {
		return myField.isSheep(x, y);
	}
}