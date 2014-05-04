package io.jrat.stub.packets.outgoing;

import io.jrat.common.codec.Hex;
import io.jrat.common.io.StringWriter;
import io.jrat.stub.Main;

import java.io.DataOutputStream;

public class Packet1InitHandshake extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {		
		String data = Main.getPass();
				
		dos.write(Hex.decodeToBytes(data));
	}

	@Override
	public byte getPacketId() {
		return (byte) 1;
	}

}
