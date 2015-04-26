package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.utils.ScreenUtils;

public class Packet20Headless extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeBoolean(ScreenUtils.isHeadless());
	}

	@Override
	public byte getPacketId() {
		return (byte) 20;
	}

}
