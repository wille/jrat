package se.jrat.stub;

import java.awt.TrayIcon;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import se.jrat.common.crypto.Crypto;

public class Configuration {
	
	private static Map<String, String> config;

	public static Map<String, String> getConfig() throws Exception {
		if (config != null) {
			return config;
		}
		
		InputStream keyFileInputStream = Main.class.getResourceAsStream("/key.dat");
		byte[] keyBuffer = new byte[keyFileInputStream.available()];
		keyFileInputStream.read(keyBuffer);
		byte[] key = keyBuffer;

		InputStream configFileInputStream = Main.class.getResourceAsStream("/config.dat");
		byte[] configBuffer = new byte[configFileInputStream.available()];
		configFileInputStream.read(configBuffer);
		String rawConfigFile = new String(Crypto.decrypt(configBuffer, key));

		config = new HashMap<String, String>();

		String[] configr = rawConfigFile.trim().split("SPLIT");

		for (int i = 0; i < configr.length; i++) {
			String str = configr[i];
			String ckey = str.substring(0, str.indexOf("=")).trim();

			String cval = str.substring(str.indexOf("=") + 1, str.length()).trim();

			config.put(ckey, cval);
		}

		return config;
	}

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

}
