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



package org.sd.battlesheep.model.client.field;



/**
 * Classe per il campo di gioco del giocatore.
 * 
 * Il campo di gioco del giocatore è caratterizzato da un numero di righe ed un
 * numero di colonne che moltiplicate ne danno il numero di celle. Nel campo di
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
	 * crea un campo di gioco per il giocatore
	 * 
	 * @param sheeps - la posizione delle pecore nel campo di gioco
	 */
	public MyField(boolean[][] sheeps) {
		super(sheeps.length, sheeps[0].length);
		this.field = new char[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				this.field[r][c] = sheeps[r][c] ? SHEEP : GRASS;
	}
	
	
	
	/**
	 * funzione che si occupa di restituire il numero di pecore vive nel campo
	 * di gioco
	 * 
	 * @return il numero di pecore vive nel campo di gioco
	 */
	@Override
	public int getAliveSheepsNumber() {
		int sheeps = 0;
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				sheeps += (field[r][c] == SHEEP) ? 1 : 0;
		return sheeps;
	}
	
	
	
	/**
	 * funzione che si occupa di settare una cella come colpita
	 * 
	 * @param r - l'indice della riga che identifica la cella
	 * @param c - l'indice della colonna che identifica la cella
	 * @return true se è stata colpita una pecora, false altrimenti
	 */
	public boolean setHit(int r, int c) {
		if (field[r][c] == SHEEP) {
			field[r][c] = HIT_SHEEP;
			return true;
		} else {
			field[r][c] = HIT_GRASS;
			return false;
		}
	}
}