package org.sd.battlesheep.communication.lobby;

public interface DiscoveryInterface {

	public void onNewLobbyFound(String host, String name);
	
	public void onDiscoveryFinished();
	
}
