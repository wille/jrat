package pro.jrat.common.hash;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

import pro.jrat.common.codec.Hex;

public class Sha256 {
	
	public static byte[] sha256(byte[] data) throws Exception {	
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		
		return digest.digest(data);
	}
	
	public static String sha256(String data) throws Exception {
		return Hex.encode(sha256(data.getBytes("UTF-8")));
	}
	
	public static String sha1(File file) throws Exception {
		byte[] contents = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		in.read(contents);
		in.close();
		
		return Hex.encode(sha256(contents));
	}

}
