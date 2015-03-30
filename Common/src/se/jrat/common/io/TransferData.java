package se.jrat.common.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class TransferData {
	
	public String remote;
	public File local;
	public OutputStream out;
	public int read;
	public long total;
	
	public TransferData() {
		
	}

	public OutputStream getOutputStream() throws Exception {
		if (out == null) {
			out = new FileOutputStream(local);
		}
		
		return out;
	}
	
	public void increaseRead(int read) {
		this.read += read;
	}
	
}