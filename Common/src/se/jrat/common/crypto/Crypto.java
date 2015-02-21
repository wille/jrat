package se.jrat.common.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypto {

	public static final int RSA_SIZE = 1024;
	public static final int KEY_LENGTH = 16;
	public static final int IV_LENGTH = 16;
	
	public static String encrypt(String unencryptedString, byte[] key) throws Exception {
		return encrypt(unencryptedString, key, "AES");
	}

	public static String decrypt(String encryptedString, byte[] key) throws Exception {
		return decrypt(encryptedString, key, "AES");
	}
	
	public static byte[] encrypt(byte[] b, byte[] key) throws Exception {
		return encrypt(b, key, "AES");
	}

	public static byte[] decrypt(byte[] b, byte[] key) throws Exception {
		return decrypt(b, key, "AES");
	}

	public static String encrypt(String unencryptedString, byte[] key, String algo) throws Exception {
		return new BASE64Encoder().encode(encrypt(unencryptedString.getBytes("UTF-8"), key, algo));
	}

	public static String decrypt(String encryptedString, byte[] key, String algo) throws Exception {
		byte[] decoded = new BASE64Decoder().decodeBuffer(encryptedString);

		return new String(decrypt(decoded, key, algo), "UTF-8");
	}

	public static byte[] decrypt(byte[] bytes, byte[] key, String algo) throws Exception {
		Cipher cipher = Cipher.getInstance(algo);

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algo));

		return cipher.doFinal(bytes);
	}

	public static byte[] encrypt(byte[] bytes, byte[] key, String algo) throws Exception {
		Cipher cipher = Cipher.getInstance(algo);

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algo));

		return cipher.doFinal(bytes);
	}
	
	public static byte[] decrypt(byte[] bytes, Key key, String algo) throws Exception {
		Cipher cipher = Cipher.getInstance(algo);

		cipher.init(Cipher.DECRYPT_MODE, key);

		return cipher.doFinal(bytes);
	}

	public static byte[] encrypt(byte[] bytes, Key key, String algo) throws Exception {
		Cipher cipher = Cipher.getInstance(algo);

		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(bytes);
	}

}