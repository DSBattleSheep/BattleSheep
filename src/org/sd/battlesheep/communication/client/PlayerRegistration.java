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
import java.rmi.AccessException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.communication.lobby.LobbyJoinRemoteInterface;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public class PlayerRegistration
{
	public static Map<String, NetPlayer> join(String lobbyAddress, String username, int playerPort) 
			throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException, UnmarshalException, UsernameAlreadyTakenException, AccessException, ConnectIOException {
		
		Map<String, NetPlayer> playerMap;
		
		Registry registry = LocateRegistry.getRegistry(lobbyAddress, CommunicationConst.LOBBY_PORT);
		LobbyJoinRemoteInterface serverInterface = (LobbyJoinRemoteInterface) registry.lookup(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);	
		
		playerMap = serverInterface.joinLobby(username, playerPort);
		
		playerMap.remove(username);
		
		return playerMap;	
	}
}