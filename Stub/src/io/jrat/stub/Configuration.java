package io.jrat.stub;

import io.jrat.common.crypto.Crypto;
import io.jrat.common.crypto.CryptoUtils;
import io.jrat.stub.utils.Utils;

import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
	private static long installms;
	

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
	
	public static Map<String, String> getConfig() throws Exception {
		if (config != null) {
			return config;
		}
		
		addresses = getConfig().get("addresses").split(",");
		id = getConfig().get("id");
		pass = getConfig().get("pass");
		connectionDelay = Long.parseLong(getConfig().get("reconsec"));
		name = getConfig().get("name");
		errorLogging = Boolean.parseBoolean(getConfig().get("error"));
		debugMessages = Boolean.parseBoolean(getConfig().get("debugmsg"));

		if (Boolean.parseBoolean(getConfig().get("timeout"))) {
			timeout = Integer.parseInt(getConfig().get("toms"));
		} else {
			timeout = 1000 * 15;
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
			    installms = bb.getLong();
				zip.close();
			}
		} else {
			key = null;
			installms = System.currentTimeMillis();
		}
			
        Date da = new Date(installms);
        Configuration.date = da.toString();

		
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
	
	/**
	 * Overwrites the encryption key and initialization vector
	 */
	public static void wipeKeys() {
		for (int i = 0; i < key.length; i++) {
			key[i] = 0;
		}
		
		for (int i = 0; i < iv.length; i++) {
			iv[i] = 0;
		}
	}

	public static boolean running = true;
	public static String date;
	public static int timeout;
	public static boolean errorLogging = false;
	public static boolean debugMessages = true;
	public static TrayIcon icon;

	public static String[] getAddresses() {
		return addresses;
	}

	public static String getID() {
		return id;
	}
	
	public static String getName() {
		return name;
	}

	public static String getPass() {
		return pass;
	}
	
	public static long getConnectionDelay() {
		return connectionDelay;
	}

}
