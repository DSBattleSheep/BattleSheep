package lobby.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import lobby.model.NetPlayer;

public interface JoinInterface extends Remote {
	
	public List<NetPlayer> JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException;
}