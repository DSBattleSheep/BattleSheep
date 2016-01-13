package lobby.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import lobby.model.NetPlayer;
import lobby.view.LobbyFrameObserver;
import lobby.view.LobbyStartObserver;

public class LobbyServerRMI extends UnicastRemoteObject implements JoinInterface,LobbyStartObserver{

	private List<NetPlayer> playerList;
	private LobbyFrameObserver frameobs;
	
	public LobbyServerRMI(int port, LobbyFrameObserver frameobs) throws RemoteException, AlreadyBoundException {
		super();
		playerList= new ArrayList<NetPlayer>();
		Registry registry=LocateRegistry.createRegistry(port);
		registry.bind("lobby", this);
		this.frameobs=frameobs;
	}

	@Override
	public void JoinLobby(String username, int port) throws RemoteException, ServerNotActiveException {
		synchronized(playerList) {
			String host=getClientHost();
			System.out.println("ip is "+host);
			NetPlayer player=new NetPlayer(username, host, port);
			playerList.add(player);
			frameobs.onClientJoin(username, host, port);
		}
	}



	@Override
	public void onLobbyStartClick() {
		for(NetPlayer p: playerList) {
			//chiama i regaz e digli OK
			System.out.println("On lobby start click");
		}
	}
	
	
	

}