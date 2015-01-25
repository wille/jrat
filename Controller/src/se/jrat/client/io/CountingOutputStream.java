package se.jrat.client.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends FilterOutputStream {

	private long count;
	
	public CountingOutputStream(OutputStream arg0) {
		super(arg0);
	}

	@Override
	public void write(int arg0) throws IOException {
		super.write(arg0);

		if (arg0 >= 0) {
			count++;
		}
	}
	
	@Override
	public void write(byte[] array, int i, int i1) throws IOException {
		super.write(array, i, i1);
		count += i1 - i;
	}
	
	@Override
	public void write(byte[] array) throws IOException {
		super.write(array);
		count += array.length;
	}
	
	public long getCount() {
		return count;
	}
	
	public void reset() {
		count = 0;
	}

}