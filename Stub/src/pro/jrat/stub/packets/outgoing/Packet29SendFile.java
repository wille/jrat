package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

public class Packet29SendFile extends AbstractOutgoingPacket {

	private String name;
	private String absolutePath;

	public Packet29SendFile(String name, String absolutePath) {
		this.name = name;
		this.absolutePath = absolutePath;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(name);
		sw.writeLine(absolutePath);
	}

	@Override
	public byte getPacketId() {
		return 29;
	}

}
