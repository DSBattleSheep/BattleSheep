package org.sdbattlesheep.lobby.view;

public interface LobbyJoinFrameObserver {

	public void onClientJoin(String username, String host, int port);
	
}
