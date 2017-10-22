package io.jrat.controller.ip2c;

import java.io.IOException;

public interface RandomAccessInput {
	int readInt() throws IOException;

	short readShort() throws IOException;

	void readFully(byte[] paramArrayOfByte) throws IOException;

	void seek(long paramLong) throws IOException;
}
