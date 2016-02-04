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
 * Classe per il campo di gioco.
 * 
 * Il campo di gioco è caratterizzato da una altezza ed una larghezza
 * che moltiplicate ne danno il numero di celle.
 * 
 * @author Giulio Biagini
 */
public abstract class AField
{
	/**
	 * la larghezza del campo di gioco
	 */
	protected int width;
	
	/**
	 * l'altezza del campo di gioco
	 */
	protected int height;
	
	/**
	 * il campo di gioco
	 */
	protected char[][] field;
	
	
	
	/**
	 * crea un campo di gioco di dimensioni in input
	 * 
	 * @param width - la larghezza del campo di gioco
	 * @param height - l'altezza del campo di gioco
	 */
	public AField(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	
	
	/**
	 * funzione che si occupa di restituire il numero di pecore vive nel campo
	 * di gioco
	 * 
	 * @return il numero di pecore vive nel campo di gioco
	 */
	public abstract int getAliveSheepsNumber();
	
	
	
	/**
	 * funzione che si occupa di restituire la larghezza del campo di
	 * gioco
	 * 
	 * @return  la larghezza del campo di gioco
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * funzione che si occupa di restituire l'altezza del campo di
	 * gioco
	 * 
	 * @return l'altezza del campo di gioco
	 */
	public int getHeight() {
		return height;
	}
}