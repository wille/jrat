package io.jrat.common.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class KeyExchanger {
	
	private DataInputStream dis;
	private DataOutputStream dos;
	private KeyPair pair;
	private PublicKey remoteKey;
	
	public KeyExchanger(DataInputStream dis, DataOutputStream dos, KeyPair pair) {
		this.dis = dis;
		this.dos = dos;
		this.pair = pair;
	}
	

	/*
	  
	 Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.DECRYPT_MODE, privKey);

		return cipher.doFinal(key);
		
	public void writeAESKey(SecretKey aesKey) throws Exception {
		PublicKey remotePubKey = getRemotePublicKey();
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, remotePubKey);

		byte[] encryptedKey = cipher.doFinal(aesKey.getEncoded());
		
		dos.writeInt(encryptedKey.length);
		dos.write(encryptedKey);
	}	*/
	
	public void readRemotePublicKey() throws Exception {
		int len = dis.readInt();
		byte[] key = new byte[len];
		
		dis.readFully(key);
		
		this.remoteKey = KeyUtils.getPublicKey(key);
	}
	
	public void writePublicKey() throws Exception {
		byte[] encoded = pair.getPublic().getEncoded();
		
		dos.writeInt(encoded.length);
		dos.write(encoded);
	}

	public PublicKey getRemoteKey() {
		return remoteKey;
	}

}
