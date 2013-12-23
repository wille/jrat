package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet50UpdateRemoteScreen extends AbstractOutgoingPacket {
	
	private int monitor;
	private int quality;
	private int size;

	public Packet50UpdateRemoteScreen(int monitor, int quality, int size) {
		this.monitor = monitor;
		this.quality = quality;
		this.size = size;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(monitor);
		dos.writeInt(quality);
		dos.writeInt(size);
	}

	@Override
	public byte getPacketId() {
		return 50;
	}

}
