package utils;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utils {

	/**
	 * Finds a local, non-loopback, IPv4 address
	 * 
	 * @return The first non-loopback IPv4 address found, or <code>null</code>
	 *         if no such addresses found
	 * @throws SocketException
	 *             If there was a problem querying the network interfaces
	 */
	public static InetAddress getLocalAddress() {
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
			while (ifaces.hasMoreElements()) {
				NetworkInterface iface = ifaces.nextElement();
				Enumeration<InetAddress> addresses = iface.getInetAddresses();
	
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					//System.out.println("addr: " + addr.getHostAddress());
					if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
						return addr;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

}