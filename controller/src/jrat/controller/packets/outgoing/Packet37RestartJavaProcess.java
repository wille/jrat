package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;


public class Packet37RestartJavaProcess extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {
		slave.closeSocket(new CloseException("Restarting process"));
	}

	@Override
	public short getPacketId() {
		return 37;
	}

}
