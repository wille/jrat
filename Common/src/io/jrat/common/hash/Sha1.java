package io.jrat.common.hash;

import io.jrat.common.codec.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;


public class Sha1 {

	public static String sha1(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");

		return Hex.encode(digest.digest(data));
	}

	public static String sha1(String data) throws Exception {
		return sha1(data.getBytes("UTF-8"));
	}

	public static String sha1(File file) throws Exception {
		byte[] contents = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		in.read(contents);
		in.close();

		return sha1(contents);
	}

}
