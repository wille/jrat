package pro.jrat.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypto {

	public static final int KEY_LENGTH = 16;
	public static final String ENCRYPTION_ALGORITHM = "AES";

	public static String encrypt(String unencryptedString, byte[] key) throws Exception {
		return new BASE64Encoder().encode(encrypt(unencryptedString.getBytes("UTF-8"), key));
	}

	public static String decrypt(String encryptedString, byte[] key) throws Exception {
		byte[] decoded = new BASE64Decoder().decodeBuffer(encryptedString);

		return new String(decrypt(decoded, key), "UTF-8");
	}

	public static byte[] encrypt(byte[] bytes, EncryptionKey key) throws Exception {
		return encrypt(bytes, key.getKey());
	}

	public static byte[] decrypt(byte[] bytes, EncryptionKey key) throws Exception {
		return decrypt(bytes, key.getKey());
	}

	public static byte[] decrypt(byte[] bytes, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ENCRYPTION_ALGORITHM));

		return cipher.doFinal(bytes);
	}

	public static byte[] encrypt(byte[] bytes, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ENCRYPTION_ALGORITHM));

		return cipher.doFinal(bytes);
	}

}