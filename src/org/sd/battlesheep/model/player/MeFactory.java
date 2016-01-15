package org.sd.battlesheep.model.player;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.util.Random;

import org.sd.battlesheep.model.MaxPortRetryException;

public class MeFactory {
	
	public static final int MAX_RETRY = 3;
	
	public static final int DEFAULT_PORT = 20000;
	
	
	
	public static Me NewMe(String username, boolean[][] sheeps, Remote remote) throws RemoteException, MaxPortRetryException {
		
		int retry = 0;
		int port = DEFAULT_PORT;
		boolean notBound = true;
		Random randomGenerator = new Random();
		
		while (retry < MAX_RETRY && notBound) {
			try {
				Registry registry = LocateRegistry.createRegistry(port);
				registry.bind("lobby", remote); //FIXME static final room name
				notBound = false;
			} catch (AlreadyBoundException | ExportException e) {
				retry++;
				port = 20000 + randomGenerator.nextInt(45535);
				if (retry == MAX_RETRY)
					throw new MaxPortRetryException("MaxPortRetryException");
			}
		}
		
		System.out.println(username + " bindato su porta: " + port);
		return new Me(username, port, sheeps);
		
	}
	
}
