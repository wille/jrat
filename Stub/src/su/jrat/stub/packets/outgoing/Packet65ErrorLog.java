package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet65ErrorLog extends AbstractOutgoingPacket {

	private String error;

	public Packet65ErrorLog(String error) {
		this.error = error;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(error);
	}

	@Override
	public byte getPacketId() {
		return 65;
	}

}
