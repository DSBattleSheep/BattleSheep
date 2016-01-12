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



package view.registration;



/**
 * Interfaccia per gli osservatori del SheepsPanel
 * 
 * @author Giulio Biagini
 */
public interface SheepsPanelObserver
{
	/**
	 * funzione che si occupa di notificare l'osservatore dell'evento relativo
	 * al click sul bottone "previous"
	 */
	public void onPreviousClick();
	
	/**
	 * funzione che si occupa di notificare l'osservatore dell'evento relativo
	 * al click sul bottone "registration"
	 * 
	 * @param sheeps - la posizione delle pecore inserite nel pannello
	 */
	public void onRegistrationClick(boolean[][] sheeps);
}