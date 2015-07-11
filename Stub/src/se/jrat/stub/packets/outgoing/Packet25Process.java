package se.jrat.stub.packets.outgoing;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import javax.imageio.ImageIO;

import se.jrat.common.ProcessData;
import se.jrat.common.io.StringWriter;


public class Packet25Process extends AbstractOutgoingPacket {

	private ProcessData processData;

	public Packet25Process(ProcessData processData) {
		this.processData = processData;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String[] data = processData.getData();
		
		assert data.length <= Byte.MAX_VALUE;
		
		dos.writeByte(data.length);
		
		for (int i = 0; i < data.length; i++) {
			sw.writeLine(data[i]);
		}
		
		boolean writeIcon = processData.getImage() != null;
		
		dos.writeBoolean(writeIcon);
		if (writeIcon) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ImageIO.write(processData.getImage(), "png", baos);
			
			byte[] image = baos.toByteArray();
			dos.writeInt(image.length);
			dos.write(image);
		}
	}

	@Override
	public byte getPacketId() {
		return 25;
	}

}
