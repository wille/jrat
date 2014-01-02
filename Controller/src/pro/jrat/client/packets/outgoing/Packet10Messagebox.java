package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

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
	public byte getPacketId() {
		return 10;
	}

}
