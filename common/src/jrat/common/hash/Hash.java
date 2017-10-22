package jrat.common.hash;

import jrat.common.codec.Hex;

import java.io.File;
import java.io.FileInputStream;

public abstract class Hash {

	public abstract byte[] hash(byte[] data) throws Exception;

	public byte[] hash(String data) throws Exception {
		return hash(data.getBytes("UTF-8"));
	}
	
	public String hashToString(String data) throws Exception {
		return Hex.encode(hash(data.getBytes("UTF-8")));
	}

	public String hash(File file) throws Exception {
		byte[] contents = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		in.read(contents);
		in.close();

		return Hex.encode(hash(contents));
	}
	
}
