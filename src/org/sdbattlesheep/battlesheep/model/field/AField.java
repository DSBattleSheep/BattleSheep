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



package org.sdbattlesheep.battlesheep.model.field;



/**
 * Classe per il campo di gioco.
 * 
 * Il campo di gioco è caratterizzato da un numero di righe ed un numero di
 * colonne che moltiplicate ne danno il numero di celle.
 * 
 * @author Giulio Biagini
 */
public abstract class AField
{
	/**
	 * il numero di righe del campo di gioco
	 */
	protected int rows;
	
	/**
	 * il numero di colonne del campo di gioco
	 */
	protected int cols;
	
	/**
	 * il campo di gioco
	 */
	protected char[][] field;
	
	
	
	/**
	 * crea un campo di gioco di dimensioni in input
	 * 
	 * @param rows - il numero di righe del campo di gioco
	 * @param cols - il numero di colonne del campo di gioco
	 */
	public AField(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}
	
	
	
	/**
	 * funzione che si occupa di restituire il numero di pecore vive nel campo
	 * di gioco
	 * 
	 * @return il numero di pecore vive nel campo di gioco
	 */
	public abstract int getAliveSheepsNumber();
	
	
	
	/**
	 * funzione che si occupa di restituire il numero di righe del campo di
	 * gioco
	 * 
	 * @return  il numero di righe del campo di gioco
	 */
	public int getRowsNumber() {
		return rows;
	}
	
	/**
	 * funzione che si occupa di restituire il numero di colonne del campo di
	 * gioco
	 * 
	 * @return il numero di colonne del campo di gioco
	 */
	public int getColsNumber() {
		return cols;
	}
}