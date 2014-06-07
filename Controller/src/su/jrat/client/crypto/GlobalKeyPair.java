package su.jrat.client.crypto;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import su.jrat.client.Globals;
import su.jrat.client.utils.IOUtils;
import su.jrat.common.Logger;
import su.jrat.common.crypto.Crypto;
import su.jrat.common.crypto.KeyUtils;

public class GlobalKeyPair {
		
	private static KeyPair global;
	
	public static void initialize() throws Exception {
		File pubKeyFile = new File(Globals.getRSAKeysDirectory(), "public.key");
		File privKeyFile = new File(Globals.getRSAKeysDirectory(), "private.key");
		
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
