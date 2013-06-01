package com.redpois0n.crypto;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Cryption {

	public static byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };

	public static AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
	
	public static Key generateKey(String key, String ALGORITHM) {
		Key keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
		return keySpec;
	}

	public static Key generateDESKey(String key) {
		Key keySpec = new SecretKeySpec(key.substring(0, 8).getBytes(), "DES");
		return keySpec;
	}

}
