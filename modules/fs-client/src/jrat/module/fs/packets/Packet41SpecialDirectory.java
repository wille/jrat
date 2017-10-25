package jrat.module.fs.packets;

import jrat.client.packets.incoming.AbstractIncomingPacket;
import jrat.common.DropLocations;
import jrat.client.Connection;
import oslib.OperatingSystem;

public class Packet41SpecialDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int location = con.readByte();

		String ret = null;

		switch (location) {
			case DropLocations.DESKTOP:
				ret = System.getProperty("user.home") + "/Desktop/";
				break;
			case DropLocations.APPDATA:
				if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					ret = System.getenv("APPDATA");
				} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
					ret = System.getProperty("user.home") + "Library/Application Support/";
				} else {
					ret = System.getProperty("java.io.tmpdir");
				}
				break;
			case DropLocations.TEMP:
				ret = System.getProperty("java.io.tmpdir");
				break;
			case DropLocations.HOME:
				ret = System.getProperty("user.home");
				break;
			default:
				return;
		}

		if (ret != null) {
			con.addToSendQueue(new Packet34CustomDirectory(ret));
		}
	}

}
