package org.sd.battlesheep.model.lobby;



import java.io.Serializable;



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