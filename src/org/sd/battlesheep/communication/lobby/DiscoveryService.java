/*
 * Battlesheep is a funny remake of the famous Battleship game, developed
 * as a distributed system.
 * 
 * Copyright (C) 2016 - Giulio Biagini, Michele Corazza, Gianluca Iselli
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sd.battlesheep.communication.lobby;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.sd.battlesheep.communication.CommunicationConst;


/**
 * @author Michele Corazza, Gianluca Iselli
 */
public class DiscoveryService {

	
	private String subnet;
	
	private DiscoveryInterface observer;
	
	
	private class WorkerThread implements Runnable {

		private String ipv4Address;

		private DiscoveryInterface observer;
		
		public WorkerThread(String ipv4Address, DiscoveryInterface observer) {
			this.ipv4Address = ipv4Address;
			this.observer = observer;
		}

		@Override
		public void run() {
			System.out.println("Connecting to: " + ipv4Address);

			try {
				Registry registry = LocateRegistry.getRegistry(ipv4Address, CommunicationConst.LOBBY_PORT);
				LobbyDiscoveryRemoteInterface discoveryInterface = (LobbyDiscoveryRemoteInterface) registry
						.lookup(CommunicationConst.LOBBY_DEFAULT_ROOM_NAME);
				observer.onNewLobbyFound(ipv4Address, discoveryInterface.retrieveLobbyName());
			} catch (RemoteException | NotBoundException | ServerNotActiveException e) {
			}
		}
	}

	
	
	public DiscoveryService(DiscoveryInterface observer) throws NumberFormatException {
		this.observer = observer;
	}
	
	public void startDiscovery(String currIpv4Address) {		
		String[] st = currIpv4Address.split("\\.");
		if (st.length != 4)
			throw new NumberFormatException("Invalid IP address: " + currIpv4Address);
		 
		subnet = st[0] + "." + st[1] + "." + st[2] + ".";
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(100);
				
				for (int i = 0; i < 255; i++)
					executor.execute(new WorkerThread(subnet + i, observer));
				
				executor.shutdown();
				 
				while (!executor.isTerminated()) { 
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) { }
				}
				
				observer.onDiscoveryFinished();
			}
		}).start();
	}
	
	public void searchOnlyLocalhost() {
		new Thread(new WorkerThread("127.0.0.1", observer)).start();
	}

}
