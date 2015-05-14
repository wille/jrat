package se.jrat.common.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Random;

public class ObfuscatedStreamKeyExchanger extends StreamKeyExchanger {
		
	public ObfuscatedStreamKeyExchanger(KeyPair pair, DataInputStream dis, DataOutputStream dos) {
		super(pair, dis, dos);
	}
	
	/**
	 * Read public key from DataInputStream
	 */
	@Override
	public PublicKey readRemoteKey() throws Exception {
		byte magic = dis.readByte();
		int len = dis.readInt();
		byte[] key = new byte[len];
		
		for (int i = 0; i < len; i++) {
			dis.readByte();
			key[i] = (byte) (dis.readByte() - magic);
		}	
		
		this.remoteKey = KeyUtils.getPublicKey(key);
		
		return this.remoteKey;
	}
	
	/**
	 * Write public key to DataOutputStream
	 */
	@Override
	public void writePublicKey() throws Exception {
		byte[] encoded = pair.getPublic().getEncoded();
		
		byte magic = (byte) new Random().nextInt();
		
		dos.writeByte(magic);
		dos.writeInt(encoded.length);
		
		byte b = (byte) (Byte.MAX_VALUE / encoded.length);

		for (int i = 0; i < encoded.length; i++) {
			dos.writeByte(b * i);
			dos.writeByte(encoded[i] + magic);
		}
	}
}
