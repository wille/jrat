package com.redpois0n.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypto {

	public static final String UNICODE_FORMAT = "UTF-8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	public static final int KEY_LENGTH = 24;

	public static String encrypt(String unencryptedString, String key) throws Exception {	
		return new BASE64Encoder().encode(encrypt(unencryptedString.getBytes("UTF-8"), key));
	}

	public static String decrypt(String encryptedString, String key) throws Exception {
		byte[] decoded = new BASE64Decoder().decodeBuffer(encryptedString);
		
		return new String(decrypt(decoded, key), "UTF-8");
	}
	
	public static byte[] decrypt(byte[] bytes, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
		DESedeKeySpec ks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKey sk = skf.generateSecret(ks);
		cipher.init(Cipher.DECRYPT_MODE, sk);
		
		return cipher.doFinal(bytes);
	}
	
	public static byte[] encrypt(byte[] bytes, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
		DESedeKeySpec ks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKey sk = skf.generateSecret(ks);
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		
		return cipher.doFinal(bytes);
	}

}