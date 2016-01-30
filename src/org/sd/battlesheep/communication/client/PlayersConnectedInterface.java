package org.sd.battlesheep.communication.client;

public interface PlayersConnectedInterface {
	
	public void onTurnOwnerCanMove();
	
	public void notifyNotConnectedUser(String username);
}
