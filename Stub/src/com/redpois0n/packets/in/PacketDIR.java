package com.redpois0n.packets.in;

import com.redpois0n.Connection;
import com.redpois0n.packets.out.Header;

public class PacketDIR extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String where = Connection.readLine();
		String ret = null;
		if (where.equals("DESKTOP")) {
			ret = System.getProperty("user.home") + "/Desktop/";
		} else if (where.equals("APPDATA")) {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win")) {
				ret = System.getenv("APPDATA");
			} else if (os.contains("mac")) {
				ret = System.getProperty("user.home") + "Library/Application Support/";
			} else {
				ret = System.getProperty("java.io.tmpdir");
			}
		} else if (where.equals("TEMP")) {
			ret = System.getProperty("java.io.tmpdir");
		}
		
		if (ret != null) {
			Connection.addToSendQueue(new PacketBuilder(Header.CUSTOM_DIRECTORY, ret));
		}
	}

}
