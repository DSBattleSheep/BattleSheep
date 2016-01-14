package org.sd.battlesheep.model.server;

import java.io.Serializable;

public class NetPlayer implements Serializable {
	
	String username,host;
	int port;
	
	public NetPlayer(String username, String host, int port) {
		this.username=username;
		this.host=host;
		this.port=port;
	}
	
	public String getUsername() {
		return username;
	}
}
