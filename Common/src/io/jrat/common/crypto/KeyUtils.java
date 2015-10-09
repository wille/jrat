package io.jrat.common.crypto;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyUtils {
	
	public static final SecretKey STATIC_KEY = new SecretKeySpec(new byte[] { 16, -127, 115, 15 , 127, -15, 17, 67, -67, 44, -13, -64, -44, 43, -13, -77}, "AES");
	public static final IvParameterSpec STATIC_IV = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, });

	public static PrivateKey getPrivateKey(byte[] key) throws Exception {
		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(key));
	}
	
	public static PublicKey getPublicKey(byte[] key) throws Exception {
		return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key));
	}

}
