package se.jrat.common.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyPair;
import java.security.PublicKey;

public class ObfuscatedStreamKeyExchanger extends StreamKeyExchanger {
		
	public ObfuscatedStreamKeyExchanger(KeyPair pair, DataInputStream dis, DataOutputStream dos) {
		super(pair, dis, dos);
	}
	
	/**
	 * Read public key from DataInputStream
	 */
	public PublicKey readRemoteKey() throws Exception {
		int len = dis.readInt();
		byte[] key = new byte[len];
		
		for (int i = 0; i < len; i++) {
			key[i] = dis.readByte();
		}	
		
		this.remoteKey = KeyUtils.getPublicKey(key);
		
		return this.remoteKey;
	}
	
	/**
	 * Write public key to DataOutputStream
	 */
	public void writePublicKey() throws Exception {
		byte[] encoded = pair.getPublic().getEncoded();
		
		dos.writeInt(encoded.length);

		for (int i = 0; i < encoded.length; i++) {
			dos.writeByte(encoded[i]);
		}
	}
}
