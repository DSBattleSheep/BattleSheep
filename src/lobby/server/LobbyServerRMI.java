package lobby.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lobby.model.NetPlayer;
import lobby.view.LobbyJoinFrameObserver;

@SuppressWarnings("serial")
public class LobbyServerRMI extends UnicastRemoteObject implements LobbyJoinRemoteInterface, LobbyStartObserver
{

	private List<NetPlayer> playerList;
	
	private LobbyJoinFrameObserver lobbyJoinFrameObserver;
	
	private ReentrantLock plock;
	
	private Condition waitForStart;
	
	
	
	public LobbyServerRMI(int port) throws RemoteException, AlreadyBoundException {
		super();
		playerList = new ArrayList<NetPlayer>();
		Registry registry = LocateRegistry.createRegistry(port);
		registry.bind("lobby", this); //FIXME static final room name
		plock = new ReentrantLock();
		waitForStart = plock.newCondition();
	}
	
	public void setLobbyJoinFrameObserver(LobbyJoinFrameObserver lobbyJoinFrameObserver) {
		this.lobbyJoinFrameObserver = lobbyJoinFrameObserver;
	}

	@Override
	public List<NetPlayer> JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException {
			String host = getClientHost();
			System.out.println("ip is "+host);
			NetPlayer player = new NetPlayer(username, host, port);
			plock.lock();
			System.out.println("ip is "+host);
			playerList.add(player);
			lobbyJoinFrameObserver.onClientJoin(username, host, port);
			System.out.println("sono denter");
			try {
				waitForStart.await();
			} catch (InterruptedException e) {
				throw new RemoteException(e.getMessage());
			} finally {
				plock.unlock();
			}
			
			return playerList;
			
	}

	@Override
	public void onLobbyStartClick() {
		plock.lock();
		waitForStart.signal();
		plock.unlock();
	}
	
}