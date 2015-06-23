package se.jrat.controller.io;

import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends InputStream {
	
	private InputStream is;

	private long count;
	private long lastRead;
	
	public CountingInputStream(InputStream is) {
		this.is = is;
	}

	@Override
	public int read() throws IOException {
		int i = is.read();
		
		if (i > 0) {
			count++;
		}
		
		lastRead = System.currentTimeMillis();
		
		return i;
	}

	@Override
	public int read(byte[] array, int i, int i1) throws IOException {
		int l = is.read(array, i, i1);
		
		if (l > 0) {
			count += l;
		}

		lastRead = System.currentTimeMillis();
		
		return l;
	}
	
	@Override
	public int read(byte[] array) throws IOException {
		int i = is.read(array);
	
		if (i > 0) {
			count += i;
		}
		
		lastRead = System.currentTimeMillis();

		return i;
	}
	
	@Override
	public long skip(long n) throws IOException {
		long l = super.skip(n);
		count += l;
		return l;
	}
	
	public long getCount() {
		return count;
	}
	
	public void reset() {
		count = 0;
	}
	
	public long getLastRead() {
		return lastRead;
	}
	
}
