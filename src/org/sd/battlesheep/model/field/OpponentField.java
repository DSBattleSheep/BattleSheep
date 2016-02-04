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
 * Classe per il campo di gioco dell'avversario.
 * 
 * Il campo di gioco dell'avversario è caratterizzato da una altezza ed una larghezza 
 * che moltiplicate ne danno il numero di celle. Il campo
 * di gioco dell'avversario è inizializzato con celle identificanti erba e, man
 * mano che il gioco avanza, sono memorizzate le celle colpite (con successo o
 * meno) dal giocatore e dagli altri avversari.
 * 
 * @author Giulio Biagini
 */
public class OpponentField extends AField
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
	 * costante che identifica una pecora colpita
	 */
	private static final char HIT_SHEEP = 'S';
	
	
	
	/**
	 * il numero di pecore nel campo di gioco
	 */
	private int sheeps;
	
	
	
	/**
	 * crea un campo di gioco per un avversario
	 * 
	 * @param width - la larghezza del campo di gioco
	 * @param height - l'altezza del campo di gioco
	 * @param sheeps - il numero di pecore nel campo di gioco
	 */
	public OpponentField(int width, int height, int sheeps) {
		super(width, height);
		this.field = new char[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				field[x][y] = GRASS;
		this.sheeps = sheeps;
	}
	
	
	
	/**
	 * funzione che si occupa di restituire il numero di pecore vive nel campo
	 * di gioco
	 * 
	 * @return il numero di pecore vive nel campo di gioco
	 */
	@Override
	public int getAliveSheepsNumber() {
		return sheeps;
	}	
	
	
	/**
	 * funzione che si occupa di settare una cella come una pecora colpita
	 * 
	 * @param x - coordinata x della mappa
	 * @param y - coordinata y della mappa
	 * @param sheep - viene indicato come impostare la cella. Con true è una pecora 
	 * 				  colpita mentre con false è erba.
	 */	
	public void setHit(int x, int y, boolean sheep) {
		if(sheep) {
			field[x][y] = HIT_SHEEP;
			sheeps--;
		} else {
			field[x][y] = HIT_GRASS;
		}
	}
}