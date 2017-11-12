package jrat.module.fs.packets.upload;

import jrat.common.io.TransferData;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

public class Packet102UploadState implements OutgoingPacket {

    private TransferData.State state;
	private String remote;
	
	public Packet102UploadState(TransferData.State state, String remote) {
	    this.state = state;
	    this.remote = remote;
	}
	
	@Override
	public void write(Slave slave) throws Exception {
	    slave.writeByte(this.state.ordinal());
		slave.writeLine(this.remote);
	}

	@Override
	public short getPacketId() {
		return 102;
	}

}
