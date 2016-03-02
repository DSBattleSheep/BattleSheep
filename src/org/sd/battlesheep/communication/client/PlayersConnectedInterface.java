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

package org.sd.battlesheep.communication.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import org.sd.battlesheep.model.field.Move;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public interface PlayersConnectedInterface {
	
	public void onTurnOwnerCanMove();
	
	public void notifyNotConnectedUser(String username);
	
	public void updateMove(Move newMove);
	
	public void pushMoveUpdate(String userName, Move newMove) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException;
}
