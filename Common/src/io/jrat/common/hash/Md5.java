package io.jrat.common.hash;

import java.security.MessageDigest;

public class Md5 extends Hash {
	
	@Override
	public byte[] hash(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");

		return digest.digest(data);
	}

}
