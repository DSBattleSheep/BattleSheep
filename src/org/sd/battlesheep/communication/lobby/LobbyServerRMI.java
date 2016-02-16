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




public class LobbyServerRMI extends UnicastRemoteObject implements LobbyJoinRemoteInterface
{
	private static final long serialVersionUID = -5365778052892677266L;

	
	private Registry registry;
	
	private Map<String, NetPlayer> playerMap;
	
	private LobbyJoinInterface lobbyJoinInterface;
	
	private ReentrantLock plock;
	
	private Condition waitForStart;
	
	

	
	
	public LobbyServerRMI(int port, LobbyJoinInterface lobbyJoinInterface) throws RemoteException, AlreadyBoundException {
		super();
		this.lobbyJoinInterface = lobbyJoinInterface;
		playerMap = new HashMap<String, NetPlayer>();
		registry = LocateRegistry.createRegistry(port);
		registry.bind(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME, this);
		plock = new ReentrantLock();
		waitForStart = plock.newCondition();
	}

	@Override
	public Map<String, NetPlayer> joinLobby(String username, int port) throws RemoteException, ServerNotActiveException, UsernameAlreadyTakenException {
		String host = getClientHost();
		plock.lock();
		System.out.println("ip is "+host);
		
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
	
	public void startGame() {
		plock.lock();
		waitForStart.signalAll();
		plock.unlock();
		try {
			registry.unbind(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread active count = " + Thread.activeCount());// XXX <- "C'è qualcosa che non va" - cit. vasco
	}
}