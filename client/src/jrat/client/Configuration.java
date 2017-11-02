package jrat.client;

import jrat.client.utils.Utils;
import jrat.common.crypto.Crypto;
import jrat.common.crypto.CryptoUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

public class Configuration {
	
	private static Map<String, String> config;
	
	/**
	 * Configuration encryption key and initialization vector
	 */
	private static byte[] key;
	private static byte[] iv;
	
	/**
	 * Installation date in Unix time (process launch time if no date available)
	 */
	private static long installTime;
	
	/**
	 * Textual installation date
	 */
	private static String installationDate;

	/**
	 * Hosts to connect to in format ip:port
	 */
	private static String[] addresses;
	
	/**
	 * ID string of this stub
	 */
	private static String id;
	
	/**
	 * Authentication password
	 */
	private static String pass;
	
	/**
	 * Delay between each connection attempt in seconds
	 */
	private static long connectionDelay;

	/**
	 * File working name
	 */
	private static String name;
	
	/**
	 * Prevent all possible debug messages from reaching stdout
	 */
	private static boolean debugMessages = true;
	
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
			
			if (extra.length > 32) {
				byte[] buffer = Arrays.copyOfRange(extra, 32, Crypto.KEY_LENGTH + Crypto.IV_LENGTH + 8);
				
				ByteBuffer bb = ByteBuffer.allocate(8);
			    bb.put(buffer);
			    bb.flip();		    
			    installTime = bb.getLong();
				zip.close();
			}
		} else {
			key = null;
			installTime = System.currentTimeMillis();
		}
			
        Date da = new Date(installTime);
        installationDate = da.toString();

		
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
			String ckey = line.substring(0, line.indexOf("=")).trim();

			String cval = line.substring(line.indexOf("=") + 1, line.length()).trim();

			config.put(ckey, cval);
		}
		
		if (extra != null && extra.length > 32) {
			config.put("installed", "true");
		}
		
		reader.close();
		
		addresses = config.get("addresses").split(",");
		id = config.get("id");
		pass = config.get("pass");
		connectionDelay = Long.parseLong(config.get("reconsec"));
		name = config.get("name");
		debugMessages = Boolean.parseBoolean(config.get("debugmsg"));

		return config;
	}
	
	public static byte[] getConfigKey() {
		return key;
	}
	
	public static IvParameterSpec getIV() {
		return new IvParameterSpec(iv);
	}
	
	/**
	 * Overwrites the encryption key and initialization vector
	 */
	public static void wipeKeys() {
		if (key != null) {
			Arrays.fill(key, (byte) 0);
		}

		if (iv != null) {
			Arrays.fill(iv, (byte) 0);
		}
	}

	public static String[] getAddresses() {
		return addresses;
	}

	public static String getID() {
		return id;
	}
	
	public static String getName() {
		return name;
	}

	public static String getFileName() {
		String file = name;

		if (!file.endsWith(".jar")) {
			file += ".jar";
		}

		return file;
	}

	public static String getPass() {
		return pass;
	}
	
	public static long getConnectionDelay() {
		return connectionDelay;
	}
	
	public static String getInstallationDate() {
		return installationDate;
	}

	public static boolean showDebugMessages() {
		return debugMessages;
	}
}
