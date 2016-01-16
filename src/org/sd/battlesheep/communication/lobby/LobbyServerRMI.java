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

import javax.swing.SwingUtilities;

import org.sd.battlesheep.communication.CommunicationConst;
import org.sd.battlesheep.model.UsernameAlreadyTakenException;
import org.sd.battlesheep.model.lobby.NetPlayer;
import org.sd.battlesheep.view.lobby.LobbyJoinFrameObserver;

@SuppressWarnings("serial")
public class LobbyServerRMI extends UnicastRemoteObject implements LobbyJoinRemoteInterface, LobbyStartObserver
{

	private Map<String, NetPlayer> playerMap;
	
	private LobbyJoinFrameObserver lobbyJoinFrameObserver;
	
	private ReentrantLock plock;
	
	private Condition waitForStart;
	
	
	private class UpdateGuiThread implements Runnable {

		private LobbyJoinFrameObserver lobbyJoinFrameObserver;
		
		private String username;
		
		private String host;
		
		private int port;
		

		public UpdateGuiThread(LobbyJoinFrameObserver lobbyJoinFrameObserver, String username, String host, int port) {
			this.lobbyJoinFrameObserver = lobbyJoinFrameObserver;
			this.username = username;
			this.host = host;
			this.port = port;
		}
		
		@Override
		public void run() {
			lobbyJoinFrameObserver.onClientJoin(username, host, port);
		}
		
	}
	
	
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
	public Map<String, NetPlayer> JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException, UsernameAlreadyTakenException {
			String host = getClientHost();
			plock.lock();
			System.out.println("ip is "+host);
			
			if (playerMap.containsKey(username))
				throw new UsernameAlreadyTakenException("\"" + username + "\" has aready been taken! Please change it and try again..");
			
			NetPlayer player = new NetPlayer(username, host, port);
			playerMap.put(username, player);
			SwingUtilities.invokeLater(new UpdateGuiThread(lobbyJoinFrameObserver, username, host, port));
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
		waitForStart.signalAll();
		plock.unlock();
	}
	
}