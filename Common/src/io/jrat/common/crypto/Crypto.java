package io.jrat.common.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {

	public static final int RSA_SIZE = 1024;
	public static final int KEY_LENGTH = 16;
	public static final int IV_LENGTH = 16;

	public static byte[] encrypt(byte[] b, byte[] key) throws Exception {
		return encrypt(b, key, "AES");
	}

	public static byte[] decrypt(byte[] b, byte[] key) throws Exception {
		return decrypt(b, key, "AES");
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