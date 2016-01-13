package lobby.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import game.model.player.Opponent;

public interface JoinInterface extends Remote {
	
	public void JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException;
}