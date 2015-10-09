package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet20KillProcess extends AbstractOutgoingPacket {

	private String process;

	public Packet20KillProcess(String process) {
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
