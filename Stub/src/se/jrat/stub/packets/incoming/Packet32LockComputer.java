package se.jrat.stub.packets.incoming;

import com.redpois0n.oslib.OperatingSystem;

public class Packet32LockComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown /l /f");
		}
	}

}
