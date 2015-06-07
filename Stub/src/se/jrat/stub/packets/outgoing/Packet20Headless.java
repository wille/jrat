package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.common.utils.Utils;

public class Packet20Headless extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeBoolean(Utils.isHeadless());
	}

	@Override
	public byte getPacketId() {
		return (byte) 20;
	}

}
