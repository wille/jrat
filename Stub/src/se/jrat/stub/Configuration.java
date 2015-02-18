package se.jrat.stub;

import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.stub.utils.Utils;

public class Configuration {
	
	private static Map<String, String> config;

	public static Map<String, String> getConfig() throws Exception {
		if (config != null) {
			return config;
		}
		
		byte[] key = new byte[Crypto.KEY_LENGTH];
		
		InputStream is;
		
		if (Utils.getJarFile().isFile()) {
			ZipFile zip = new ZipFile(Utils.getJarFile());
			key = zip.getEntry("config.dat").getExtra();
			zip.close();
		} else if ((is = Main.class.getResourceAsStream("/key.dat")) != null) {
			is.read(key);
		} else {
			key = null;
		}

		
		if (key == null) {
			is = Main.class.getResourceAsStream("/config.dat");
		} else {
			Cipher cipher = CryptoUtils.getBlockCipher(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
			is = new CipherInputStream(Main.class.getResourceAsStream("/config.dat"), cipher);
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		config = new HashMap<String, String>();

		String line;
		
		while ((line = reader.readLine()) != null) {
			String str = line;
						
			String ckey = str.substring(0, str.indexOf("=")).trim();

			String cval = str.substring(str.indexOf("=") + 1, str.length()).trim();

			config.put(ckey, cval);
		}
		
		reader.close();

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
