package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.common.utils.Utils;

public class Packet20Headless implements OutgoingPacket {

	@Override
	public void write(Connection dos) throws Exception {
		dos.writeBoolean(Utils.isHeadless());
	}

	@Override
	public short getPacketId() {
		return (byte) 20;
	}

}
