package pro.jrat.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import pro.jrat.common.listeners.CopyStreamsListener;

public class IOUtils {

	public static long copy(InputStream input, OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int n = 0;
		long count = 0L;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
	
	public static long copy(long total, InputStream input, OutputStream output, CopyStreamsListener listener) throws Exception {
		byte[] buffer = new byte[1024];
		int n = 0;
		long count = 0L;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
			count += n;
			listener.chunk(count, total, MathUtils.getPercentFromTotal((int) count, (int) total));
		}
		return count;
	}

	public static byte[] toByteArray(InputStream input) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

}
