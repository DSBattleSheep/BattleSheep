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

package org.sd.battlesheep.communication.lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;

import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public interface LobbyJoinRemoteInterface extends Remote {
	
	public Map<String, NetPlayer> joinLobby(String username, int port) throws RemoteException, ServerNotActiveException, UsernameAlreadyTakenException;
}