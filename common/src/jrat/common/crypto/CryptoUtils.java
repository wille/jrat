package jrat.common.crypto;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class CryptoUtils {
	
	public static final String STANDARD_STREAM_ALGO = "AES/CTR/PKCS5PADDING";
	public static final String STANDARD_BLOCK_ALGO = "AES/CBC/PKCS5PADDING";
	
	public static Cipher getStreamCipher(int mode, Key key, IvParameterSpec iv) throws Exception {
		Cipher cipher = getCipher(mode, STANDARD_STREAM_ALGO, key, iv);
		
		return cipher;
	}
	
	public static Cipher getBlockCipher(int mode, Key key, IvParameterSpec iv) throws Exception {
		Cipher cipher = getCipher(mode, STANDARD_BLOCK_ALGO, key, iv);
		
		return cipher;
	}
	
	public static Cipher getCipher(int mode, String algo, Key key, IvParameterSpec iv) throws Exception {
		Cipher cipher = Cipher.getInstance(algo);
		
		cipher.init(mode, key, iv);
		
		return cipher;
	}
	
	public static IvParameterSpec getRandomIv() {
		return getRandomIv(Crypto.IV_LENGTH);
	}
	
	public static IvParameterSpec getRandomIv(int len) {
		byte[] b = new byte[len];
		Random rr = new Random();
		rr.nextBytes(b);
		
		return new IvParameterSpec(b);
	}

}
