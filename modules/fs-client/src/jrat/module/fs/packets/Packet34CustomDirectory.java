package jrat.module.fs.packets;

import jrat.client.packets.outgoing.AbstractOutgoingPacket;
import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 34;
	}

}
