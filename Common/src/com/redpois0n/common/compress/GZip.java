package com.redpois0n.common.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.redpois0n.common.utils.IOUtils;

public class GZip {
	
	public static void main(String[] args) {
		try {
			byte[] bytes = new byte[1024 * 1024];
			
			long start = System.currentTimeMillis();
			
			compress(bytes);
			
			long end = System.currentTimeMillis();
			
			System.out.println(end - start);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] compress(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPInputStream gzis = new GZIPInputStream(is);
		IOUtils.copy(gzis, baos);
		
		gzis.close();
		
		return baos.toByteArray();
	}

	public static byte[] decompress(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzos = new GZIPOutputStream(baos);
		IOUtils.copy(is, gzos);
		
		gzos.close();
		
		return baos.toByteArray();
	}

	public static byte[] compress(byte[] input) throws Exception {
		return compress(new ByteArrayInputStream(input));
	}

	public static byte[] decompress(byte[] input) throws Exception {
		return decompress(new ByteArrayInputStream(input));
	}

}
