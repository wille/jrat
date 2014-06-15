package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet54Registry extends AbstractOutgoingPacket {

	private String[] args;

	public Packet54Registry(String[] args) {
		this.args = args;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
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