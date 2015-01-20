package se.jrat.client.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends FilterInputStream {

	private long count;
	
	public CountingInputStream(InputStream arg0) {
		super(arg0);
	}

	@Override
	public int read() throws IOException {
		int i = super.read();
		
		if (i > 0) {
			count++;
		}
		
		return i;
	}

	@Override
	public int read(byte[] array, int i, int i1) throws IOException {
		int l = super.read(array, i, i1);
		
		if (i > 0) {
			count += i;
		}
		
		return l;
	}
	
	@Override
	public int read(byte[] array) throws IOException {
		int i = super.read(array);
	
		if (i > 0) {
			count += i;
		}
		
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
	
}
