package se.jrat.stub;

import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.stub.utils.Utils;

public class Configuration {
	
	private static Map<String, String> config;
	private static byte[] key;
	private static byte[] iv;

	public static Map<String, String> getConfig() throws Exception {
		if (config != null) {
			return config;
		}
		
		key = new byte[Crypto.KEY_LENGTH];
		iv = new byte[Crypto.IV_LENGTH];
		
		byte[] extra = null;
		
		InputStream is;
		
		if (Utils.getJarFile().isFile()) {
			ZipFile zip = new ZipFile(Utils.getJarFile());
			extra = zip.getEntry("config.dat").getExtra();
			key = Arrays.copyOfRange(extra, 0, Crypto.KEY_LENGTH);
			iv = Arrays.copyOfRange(extra, 16, Crypto.KEY_LENGTH + Crypto.IV_LENGTH);
			zip.close();
		} else if ((is = Main.class.getResourceAsStream("/key.dat")) != null) {
			is.read(key);
		} else {
			key = null;
		}

		
		if (key == null) {
			is = Main.class.getResourceAsStream("/config.dat");
		} else {
			Cipher cipher = CryptoUtils.getBlockCipher(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
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
		
		if (extra != null && extra.length > 32) {
			config.put("installed", "true");
		}
		
		reader.close();

		return config;
	}
	
	public static byte[] getConfigKey() {
		return key;
	}
	
	public static IvParameterSpec getIV() {
		return new IvParameterSpec(iv);
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
