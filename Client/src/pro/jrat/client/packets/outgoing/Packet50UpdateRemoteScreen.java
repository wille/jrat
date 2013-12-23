package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet50UpdateRemoteScreen extends AbstractOutgoingPacket {

	public Packet50UpdateRemoteScreen(int monitor, int quality, int size) {

	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		
	}

	@Override
	public byte getPacketId() {
		return 50;
	}

}
