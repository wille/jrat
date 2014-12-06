package se.jrat.common.hash;

import java.security.MessageDigest;

public class Sha256 extends Hash {

	@Override
	public byte[] hash(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		return digest.digest(data);
	}

}
