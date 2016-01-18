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



import java.io.Serializable;

import org.sd.battlesheep.model.field.OpponentField;
import org.sd.battlesheep.model.lobby.NetPlayer;



/**
 * Classe per l'avversario.
 * 
 * L'avversario è costituito da un username, un host ed una porta sulla quale è
 * in ascolto in attesa di una connessione da parte di altri giocatori per la
 * comunicazione. L'avversario ha poi un proprio campo di gioco nel quale
 * inizialmente è memorizzata la sola erba e progressivamente viene aggiornato
 * con le celle colpite (con successo o meno) dagli avversari e dal giocatore.
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class Opponent extends APlayer implements Serializable
{
	/**
	 * l'host dell'avversario
	 */
	private String host;

	
	/**
	 * la porta per la comunicazione con gli altri giocatori
	 */
	private int port;
	
	/**
	 * il campo di gioco dell'avversario
	 */
	private OpponentField opponentField;
	
	
	
	/**
	 * crea un avversario dato un username, le dimensioni del campo di gioco,
	 * il numero di pecore iniziale e l'indirizzo e porta dell'host sul quale
	 * risiede.
	 * 
	 * @param netPlayer - un oggetto contenente username, host e porta
	 * @param rows - il numero di righe del campo di gioco
	 * @param cols - il numero di colonne del campo di gioco
	 * @param sheeps - il numero di pecore iniziale nel campo di gioco
	 */
	public Opponent(NetPlayer netPlayer, int rows, int cols, int sheeps) {
		super(netPlayer.getUsername());
		this.host = netPlayer.getHost();
		this.port = netPlayer.getPort();
		this.opponentField = new OpponentField(rows, cols, sheeps);
	}
	
	
	
	/**
	 * funzione che si occupa di verificare se l'avversario ha perso la partita
	 * ovvero se non ha più pecore vive nel proprio campo di gioco
	 * 
	 * @return true se l'avversario ha perso la partita, false altrimenti
	 */
	@Override
	public boolean lost() {
		return opponentField.getAliveSheepsNumber() > 0;
	}
	
	
	
	/**
	 * funzione che si occupa di restituire l'host dell'avversario
	 * 
	 * @return l'host dell'avversario
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * funzione che si occupa di restituire la porta per la comunicazione 
	 * del player 
	 * 
	 * @return la porta per la comunicazione del player
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * funzione che si occupa di settare una cella come un colpo nell'erba
	 * 
	 * @param r - l'indice della riga che identifica la cella
	 * @param c - l'indice della colonna che identifica la cella
	 */
	public void setHitGrass(int r, int c) {
		opponentField.setHitGrass(r, c);
	}
	
	/**
	 * funzione che si occupa di settare una cella come una pecora colpita
	 * 
	 * @param r - l'indice della riga che identifica la cella
	 * @param c - l'indice della colonna che identifica la cella
	 */
	public void setHitSheep(int r, int c) {
		opponentField.setHitSheep(r, c);
	}
}