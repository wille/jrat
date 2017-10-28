package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public interface IncomingPacket {

	void read(Slave slave) throws Exception;
}
