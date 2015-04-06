package se.jrat.controller.io;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends OutputStream {

	private OutputStream out;
	
	private long count;
	private long lastWrite;
	
	public CountingOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int i) throws IOException {
		out.write(i);

		count++;
		
		lastWrite = System.currentTimeMillis();
	}
	
	@Override
	public void write(byte[] array, int i, int i1) throws IOException {
		out.write(array, i, i1);
		count += i1 - i;
		lastWrite = System.currentTimeMillis();
	}
	
	@Override
	public void write(byte[] array) throws IOException {
		out.write(array);
		count += array.length;
		lastWrite = System.currentTimeMillis();
	}
	
	public long getCount() {
		return count;
	}
	
	public void reset() {
		count = 0;
	}
	
	public long getLastWrite() {
		return lastWrite;
	}

}
