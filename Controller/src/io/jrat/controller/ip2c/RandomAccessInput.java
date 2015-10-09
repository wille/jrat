package io.jrat.controller.ip2c;

import java.io.IOException;

public abstract interface RandomAccessInput {
	public abstract int readInt() throws IOException;

	public abstract short readShort() throws IOException;

	public abstract void readFully(byte[] paramArrayOfByte) throws IOException;

	public abstract void seek(long paramLong) throws IOException;
}
