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



package org.sd.battlesheep.view.registration;



/**
 * Interfaccia per gli osservatori del RegistrationFrame
 * 
 * @author Giulio Biagini
 */
public interface RegistrationFrameObserver
{
	/**
	 * funzione che si occupa di notificare l'osservatore dell'evento relativo
	 * al click sul bottone "registration"
	 * 
	 * @param username - l'username del giocatore
	 * @param sheeps - la posizione delle pecore nel campo di gioco
	 */
	public void onRegistrationClick(String username, boolean[][] sheeps);
}