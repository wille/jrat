package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet12RemoteScreen extends AbstractOutgoingPacket {
	
	private double size;
	private int monitor;
	private int rows;
	private int cols;

	public Packet12RemoteScreen(double size, int monitor, int rows, int cols) {
		this.size = size;
		this.monitor = monitor;
		this.rows = rows;
		this.cols = cols;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeDouble(size);
		dos.writeInt(monitor);
		dos.writeInt(rows);
		dos.writeInt(cols);
	}

	@Override
	public byte getPacketId() {
		return 12;
	}

}
