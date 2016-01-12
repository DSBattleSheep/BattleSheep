/*
 * Battlesheep is a funny remake of the famous BattleShip game, developed
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



package model.field;



/**
 * Classe per il campo di gioco dell'avversario.
 * 
 * Il campo di gioco dell'avversario è caratterizzato da un numero di righe ed
 * un numero di colonne che moltiplicate ne danno il numero di celle. Il campo
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
	 * @param rows - il numero di righe del campo di gioco
	 * @param cols - il numero di colonne del campo di gioco
	 * @param sheeps - il numero di pecore nel campo di gioco
	 */
	public OpponentField(int rows, int cols, int sheeps) {
		super(rows, cols);
		this.field = new char[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				field[r][c] = GRASS;
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
	 * funzione che si occupa di settare una cella come un colpo nell'erba
	 * 
	 * @param r - l'indice della riga che identifica la cella
	 * @param c - l'indice della colonna che identifica la cella
	 */
	public void setHitGrass(int r, int c) {
		field[r][c] = HIT_GRASS;
	}
	
	/**
	 * funzione che si occupa di settare una cella come una pecora colpita
	 * 
	 * @param r - l'indice della riga che identifica la cella
	 * @param c - l'indice della colonna che identifica la cella
	 */
	public void setHitSheep(int r, int c) {
		field[r][c] = HIT_SHEEP;
		sheeps--;
	}
}