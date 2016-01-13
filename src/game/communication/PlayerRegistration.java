package game.communication;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

import game.model.ModelResources;
import lobby.model.NetPlayer;
import lobby.server.JoinInterface;

public class PlayerRegistration {
	
	public static void Join(String user, int port) {
		//FIXME
		List<NetPlayer> l;
		try {
			JoinInterface serverInterface = (JoinInterface) Naming.lookup("rmi://127.0.0.1:"+ModelResources.PORT_LOBBY+"/lobby");
			l=serverInterface.JoinLobby(user,port);
			System.out.println(l.get(0).getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}