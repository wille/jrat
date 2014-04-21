package io.jrat.client.crypto;

import io.jrat.client.io.Files;
import io.jrat.client.utils.IOUtils;
import io.jrat.common.Logger;
import io.jrat.common.crypto.Crypto;
import io.jrat.common.crypto.KeyUtils;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class GlobalKeyPair {
		
	private static KeyPair global;
	
	public static void initialize() throws Exception {
		File pubKeyFile = new File(Files.getFiles(), "public.key");
		File privKeyFile = new File(Files.getFiles(), "private.key");
		
		PublicKey publicKey;
		PrivateKey privateKey;
		
		if (!pubKeyFile.exists() || !privKeyFile.exists()) {
			Logger.log("Generating new key pair");
			
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(Crypto.RSA_SIZE);
			KeyPair kp = kpg.genKeyPair();
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();
			
			IOUtils.writeFile(pubKeyFile, publicKey.getEncoded());
			IOUtils.writeFile(privKeyFile, privateKey.getEncoded());	
		} else {
			try {
				publicKey = KeyUtils.getPublicKey(IOUtils.readFile(pubKeyFile));
				privateKey = KeyUtils.getPrivateKey(IOUtils.readFile(privKeyFile));
			} catch (InvalidKeySpecException ex) {
				ex.printStackTrace();
				Logger.log("Corrupt key files, generating new");
				pubKeyFile.delete();
				privKeyFile.delete();
				initialize();
				return;
			}
			
		}
		
		global = new KeyPair(publicKey, privateKey);
	}

	public static KeyPair getKeyPair() throws Exception {
		if (global == null) {
			initialize();
		}
		
		return global;
	}

}
