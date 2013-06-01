package com.redpois0n.packets;

import com.redpois0n.Connection;

public class PacketDIR extends Packet {

	@Override
	public void read(String line) throws Exception {
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
