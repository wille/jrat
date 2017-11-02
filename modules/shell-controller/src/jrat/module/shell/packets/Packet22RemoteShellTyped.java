package jrat.module.shell.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet22RemoteShellTyped implements OutgoingPacket {

	private char c;

	public Packet22RemoteShellTyped(char c) {
		this.c = c;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeChar(c);
	}

	@Override
	public short getPacketId() {
		return 22;
	}

}
