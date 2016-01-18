package org.sd.battlesheep.communication.client;

import java.rmi.Remote;

import org.sd.battlesheep.model.field.Move;

public interface MyTurnInterface extends Remote {
	
	public Move connectCurrentPlayer(String user);
	
}