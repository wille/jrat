package jrat.module.process;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import oslib.OperatingSystem;

public class Packet20KillProcess implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String process = con.readLine();

		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "taskkill", "/f", "/im", process });
			} else {
				Runtime.getRuntime().exec("kill " + process);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
