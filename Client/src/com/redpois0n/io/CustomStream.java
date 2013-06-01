package com.redpois0n.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class CustomStream {
	
	public abstract String readLine() throws Exception;
	
	public abstract void writeLine(String s) throws Exception;
	
	public abstract DataInputStream getDataInputStream();
	
	public abstract DataOutputStream getDataOutputStream();
	
	
	public void writeBoolean(boolean b) throws Exception {
		writeLine(Boolean.toString(b));
	}
	
	public boolean readBoolean() throws Exception {
		return Boolean.parseBoolean(readLine());
	}
	
	
	public void writeInt(int v) throws Exception {
		writeLine(Integer.toString(v));
	}
	
	public int readInt() throws Exception {
		return Integer.parseInt(readLine());
	}
	
	
	public void writeLong(long l) throws Exception {
		writeLine(Long.toString(l));
	}
	
	public long readLong() throws Exception {
		return Long.parseLong(readLine());
	}
	
	
	public void writeLine(Object obj) throws Exception {
		writeLine(obj.toString());
	}

}
