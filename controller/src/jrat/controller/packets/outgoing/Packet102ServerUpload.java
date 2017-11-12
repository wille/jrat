package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

public class Packet102ServerUpload implements OutgoingPacket {

    private int action;
	private String remote;
	
	public Packet102ServerUpload(int action, String remote) {
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
