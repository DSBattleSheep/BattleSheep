package org.sd.battlesheep.communication.lobby;

import java.rmi.AlreadyBoundException;
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
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.view.lobby.LobbyJoinFrameObserver;

@SuppressWarnings("serial")
public class LobbyServerRMI extends UnicastRemoteObject implements LobbyJoinRemoteInterface, LobbyStartObserver
{

	private Map<String, NetPlayer> playerMap;
	
	private LobbyJoinFrameObserver lobbyJoinFrameObserver;
	
	private ReentrantLock plock;
	
	private Condition waitForStart;
	
	
	
	public LobbyServerRMI(int port) throws RemoteException, AlreadyBoundException {
		super();
		playerMap = new HashMap<String, NetPlayer>();
		Registry registry = LocateRegistry.createRegistry(port);
		registry.bind(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME, this); //FIXME static final room name
		plock = new ReentrantLock();
		waitForStart = plock.newCondition();
	}
	
	public void setLobbyJoinFrameObserver(LobbyJoinFrameObserver lobbyJoinFrameObserver) {
		this.lobbyJoinFrameObserver = lobbyJoinFrameObserver;
	}

	@Override
	public Map<String, NetPlayer> JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException {
			String host = getClientHost();
			System.out.println("ip is "+host);
			NetPlayer player = new NetPlayer(username, host, port);
			plock.lock();
			System.out.println("ip is "+host);
			playerMap.put(username, player);
			lobbyJoinFrameObserver.onClientJoin(username, host, port);
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

	@Override
	public void onLobbyStartClick() {
		plock.lock();
		waitForStart.signal();
		plock.unlock();
	}
	
}