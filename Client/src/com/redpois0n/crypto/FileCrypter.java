package com.redpois0n.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class FileCrypter {


	@SuppressWarnings("resource")
	public void encrypt(File input, File output, String key) throws Exception {
		byte[] buffer= new byte[1024];
		
		InputStream in = new FileInputStream(input);
		OutputStream out = new FileOutputStream(output);

		Cipher ecipher = Cipher.getInstance("DESede");

		ecipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "DESede"));

		out = new CipherOutputStream(out, ecipher);

		int numRead = 0;
		while ((numRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, numRead);
		}
		out.close();
	}

	/*
	 * @SuppressWarnings("resource") public void decrypt(String inname, String
	 * outname, String key) { try { InputStream in = new
	 * FileInputStream(inname); OutputStream out = new
	 * FileOutputStream(outname);
	 * 
	 * Cipher dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	 * 
	 * dcipher.init(Cipher.DECRYPT_MODE, Cryption.generateDESKey(key),
	 * Cryption.paramSpec);
	 * 
	 * in = new CipherInputStream(in, dcipher);
	 * 
	 * int numRead = 0; while ((numRead = in.read(Cryption.buf)) >= 0) {
	 * out.write(Cryption.buf, 0, numRead); } out.close(); } catch (Exception e)
	 * { e.printStackTrace(); } }
	 * 
	 * @SuppressWarnings("resource") public void encrypt(String inname, String
	 * outname, String key) { try { InputStream in = new
	 * FileInputStream(inname); OutputStream out = new
	 * FileOutputStream(outname);
	 * 
	 * Cipher ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	 * 
	 * ecipher.init(Cipher.ENCRYPT_MODE, Cryption.generateDESKey(key),
	 * Cryption.paramSpec);
	 * 
	 * out = new CipherOutputStream(out, ecipher);
	 * 
	 * int numRead = 0; while ((numRead = in.read(Cryption.buf)) >= 0) {
	 * out.write(Cryption.buf, 0, numRead); } out.close(); } catch (Exception e)
	 * { e.printStackTrace(); } }
	 */
}
