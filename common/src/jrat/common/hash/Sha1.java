package jrat.common.hash;

import java.security.MessageDigest;

public class Sha1 extends Hash {

	@Override
	public byte[] hash(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");

		return digest.digest(data);
	}

}
