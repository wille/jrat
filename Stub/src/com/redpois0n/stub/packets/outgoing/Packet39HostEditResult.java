package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet39HostEditResult extends AbstractOutgoingPacket {

	public Packet39HostEditResult(String string) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public byte getPacketId() {
		return 39;
	}

}
