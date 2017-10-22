package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


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
