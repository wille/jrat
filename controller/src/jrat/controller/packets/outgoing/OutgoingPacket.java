package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public interface OutgoingPacket {

	void write(Slave slave) throws Exception;

	short getPacketId();
}
