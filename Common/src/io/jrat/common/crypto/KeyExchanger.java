package io.jrat.common.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class KeyExchanger {
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public KeyExchanger(DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
	}
	
	public byte[] getAESKey(PrivateKey privKey, PublicKey pubKey) throws Exception {
		writeKey(pubKey);
		
		int len = dis.readInt();
		byte[] key = new byte[len];
		
		dis.readFully(key);
		
		Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.DECRYPT_MODE, privKey);

		return cipher.doFinal(key);
	}
	
	public void writeAESKey(SecretKey aesKey) throws Exception {
		PublicKey remotePubKey = readKey();
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, remotePubKey);

		byte[] encryptedKey = cipher.doFinal(aesKey.getEncoded());
		
		dos.writeInt(encryptedKey.length);
		dos.write(encryptedKey);
	}
	
	
	
	
	
	private void writeKey(PublicKey pubKey) throws Exception {
		int pubKeyLen = pubKey.getEncoded().length;
		
		dos.writeInt(pubKeyLen);
		dos.write(pubKey.getEncoded());
	}
	
	private PublicKey readKey() throws Exception {
		int len = dis.readInt();
		byte[] key = new byte[len];
		
		dis.readFully(key);
		
		return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key));
	}

}
