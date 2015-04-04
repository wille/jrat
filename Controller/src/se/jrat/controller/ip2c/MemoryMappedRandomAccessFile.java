package se.jrat.controller.ip2c;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@SuppressWarnings("resource")
public class MemoryMappedRandomAccessFile implements RandomAccessInput {
	private ByteBuffer m_roBuf;

	public MemoryMappedRandomAccessFile(String paramString1, String paramString2) throws IOException {
		RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString1, paramString2);
		FileChannel localFileChannel = localRandomAccessFile.getChannel();
		this.m_roBuf = localFileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, (int) localFileChannel.size());
	}

	public int readInt() throws IOException {
		return this.m_roBuf.getInt();
	}

	public short readShort() throws IOException {
		return this.m_roBuf.getShort();
	}

	public void readFully(byte[] paramArrayOfByte) throws IOException {
		this.m_roBuf.get(paramArrayOfByte);
	}

	public void seek(long paramLong) throws IOException {
		this.m_roBuf.position((int) paramLong);
	}
}
