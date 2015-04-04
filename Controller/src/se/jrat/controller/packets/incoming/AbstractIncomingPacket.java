package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;


public abstract class AbstractIncomingPacket {

	public abstract void read(Slave slave, DataInputStream dis) throws Exception;

}
