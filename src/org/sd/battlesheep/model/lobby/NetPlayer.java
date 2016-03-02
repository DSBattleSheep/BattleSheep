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

package org.sd.battlesheep.model.lobby;



import java.io.Serializable;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public class NetPlayer implements Serializable {
	
	private static final long serialVersionUID = 4845436760210907430L;
	
	
	
	private String username;
	
	private String host;
	
	private int port;
	
	
	
	public NetPlayer(String username, String host, int port) {
		this.username = username;
		this.host = host;
		this.port = port;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
}