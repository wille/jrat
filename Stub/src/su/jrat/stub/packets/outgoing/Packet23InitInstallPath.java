package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet23InitInstallPath extends AbstractOutgoingPacket {

	private String path;

	public Packet23InitInstallPath(String path) {
		this.path = path;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return (byte) 23;
	}

}
