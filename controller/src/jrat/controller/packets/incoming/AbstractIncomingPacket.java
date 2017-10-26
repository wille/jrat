package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public abstract class AbstractIncomingPacket {

	public abstract void read(Slave slave) throws Exception;

}
