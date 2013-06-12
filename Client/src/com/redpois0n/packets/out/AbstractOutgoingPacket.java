package com.redpois0n.packets.out;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public abstract class AbstractOutgoingPacket {
	
	public abstract void write(Slave slave, DataOutputStream dos) throws Exception;

}
