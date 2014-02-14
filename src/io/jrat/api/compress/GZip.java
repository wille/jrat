package io.jrat.api.compress;

import io.jrat.api.utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class GZip {

	public static byte[] decompress(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPInputStream gzis = new GZIPInputStream(is);
		IOUtils.copy(gzis, baos);

		gzis.close();

		return baos.toByteArray();
	}

	public static byte[] compress(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzos = new GZIPOutputStream(baos);
		IOUtils.copy(is, gzos);

		gzos.close();

		return baos.toByteArray();
	}

	public static byte[] decompress(byte[] input) throws Exception {
		return decompress(new ByteArrayInputStream(input));
	}

	public static byte[] compress(byte[] input) throws Exception {
		return compress(new ByteArrayInputStream(input));
	}

}
