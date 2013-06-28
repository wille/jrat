package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;


public class Packet26InitJavaPath extends AbstractOutgoingPacket {

	private String path;
	
	public Packet26InitJavaPath(String path) {
		this.path = path;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return (byte) 26;
	}

}
