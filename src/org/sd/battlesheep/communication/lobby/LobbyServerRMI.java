package org.sd.battlesheep.communication.lobby;



import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;




public class LobbyServerRMI extends UnicastRemoteObject implements LobbyDiscoveryRemoteInterface, LobbyJoinRemoteInterface
{
	private static final long serialVersionUID = -5365778052892677266L;

	
	private Registry registry;
	
	private Map<String, NetPlayer> playerMap;
	
	private LobbyJoinInterface lobbyJoinInterface;
	
	private ReentrantLock plock;
	
	private Condition waitForStart;
	
	private String lobbyName;
	
	

	
	
	public LobbyServerRMI(String lobbyName, int port, LobbyJoinInterface lobbyJoinInterface) throws RemoteException, AlreadyBoundException {
		super();
		this.lobbyName = lobbyName; 
		this.lobbyJoinInterface = lobbyJoinInterface;
		playerMap = new HashMap<String, NetPlayer>();
		registry = LocateRegistry.createRegistry(port);
		registry.bind(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME, this);
		plock = new ReentrantLock();
		waitForStart = plock.newCondition();
	}
	

	@Override
	public String retrieveLobbyName() throws RemoteException, ServerNotActiveException {
		return lobbyName;
	}

	
	@Override
	public Map<String, NetPlayer> joinLobby(String username, int port) throws RemoteException, ServerNotActiveException, UsernameAlreadyTakenException {
		String host = getClientHost();
		plock.lock();
		System.out.println("ip is " + host);
		
		if (playerMap.containsKey(username)) {
			plock.unlock();
			throw new UsernameAlreadyTakenException("\"" + username + "\" has aready been taken! Please change it and try again..");
		}
		NetPlayer player = new NetPlayer(username, host, port);
		playerMap.put(username, player);
		
		lobbyJoinInterface.onClientJoin(username, host, port);
		
		System.out.println("sono denter");
		try {
			waitForStart.await();
		} catch (InterruptedException e) {
			throw new RemoteException(e.getMessage());
		} finally {
			plock.unlock();
		}
		
		return playerMap;
	}
	
	public void close() {
		try {
			registry.unbind(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);
			UnicastRemoteObject.unexportObject(this, true);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void startGame() {
		plock.lock();
		waitForStart.signalAll();
		plock.unlock();
		
		close();
	}
}