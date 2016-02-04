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



package org.sd.battlesheep.model.field;



/**
 * Classe per il campo di gioco del giocatore.
 * 
 * Il campo di gioco del giocatore è caratterizzato da una altezza ed una larghezza 
 * che moltiplicate ne danno il numero di celle. Nel campo di
 * gioco del giocatore è memorizzata la posizione dell'erba, delle pecore e
 * delle celle colpite (con successo o meno) dagli avversari.
 * 
 * @author Giulio Biagini
 */
public class MyField extends AField
{
	/**
	 * costante che identifica l'erba
	 */
	private static final char GRASS = 'g';
	
	/**
	 * costante che identifica un colpo nell'erba
	 */
	private static final char HIT_GRASS = 'G';
	
	/**
	 * costante che identifica le pecore
	 */
	private static final char SHEEP = 's';
	
	/**
	 * costante che identifica una pecora colpita
	 */
	private static final char HIT_SHEEP = 'S';
	
	
	/**
	 * contatore che indica quante sono le pecore ancora vive
	 */
	private int aliveSheepsCount;
	
	
	
	
	/**
	 * crea un campo di gioco per il giocatore
	 * 
	 * @param sheeps - la posizione delle pecore nel campo di gioco
	 */
	public MyField(boolean[][] sheeps) {
		super(sheeps.length, sheeps[0].length);
		aliveSheepsCount = 0;
		this.field = new char[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (sheeps[x][y]) {
					this.field[x][y] = SHEEP;
					aliveSheepsCount++;
				} else
					this.field[x][y] = GRASS;
	}
	
	
	
	/**
	 * funzione che si occupa di restituire il numero di pecore vive nel campo
	 * di gioco
	 * 
	 * @return il numero di pecore vive nel campo di gioco
	 */
	@Override
	public int getAliveSheepsNumber() {
		return aliveSheepsCount;
	}
	
	
	
	/**
	 * funzione che si occupa di settare una cella come colpita
	 * 
	 * @param x - coordinata x della mappa
	 * @param y - coordinata y della mappa
	 */
	public void hit(int x, int y) {
		if (field[x][y] == SHEEP) {
			field[x][y] = HIT_SHEEP;
			aliveSheepsCount--;
		} else {
			field[x][y] = HIT_GRASS;
		}
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
		return field[x][y] == SHEEP;
	}
}