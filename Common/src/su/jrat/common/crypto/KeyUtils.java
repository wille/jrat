package su.jrat.common.crypto;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtils {

	public static PrivateKey getPrivateKey(byte[] key) throws Exception {
		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(key));
	}
	
	public static PublicKey getPublicKey(byte[] key) throws Exception {
		return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key));
	}

}
