package pro.jrat.common.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class FileIO {

	public static void writeFile(File file, DataOutputStream dos, DataInputStream dis, TransferListener listener, byte[] key) throws Exception {
		FileInputStream fileInput = new FileInputStream(file);

		int chunkSize = 1024;
		byte[] chunk = new byte[chunkSize];

		long fileSize = file.length();
		dos.writeLong(fileSize);

		for (long pos = 0; pos < fileSize; pos += chunkSize) {
			fileInput.read(chunk);
		
			dos.writeInt(chunk.length);
			dos.write(chunk, 0, chunk.length);
			
			dis.readByte();

			if (listener != null) {
				listener.transferred(chunk.length, pos, fileSize);
			}
		}
		dos.writeInt(-1);
		fileInput.close();
	}

	public static void readFile(File output, DataInputStream dis, DataOutputStream dos, TransferListener listener, byte[] key) throws Exception {
		FileOutputStream fileOutput = new FileOutputStream(output);

		int read = 0;
		int chunkSize;
		
		long size = dis.readLong();
		
		while ((chunkSize = dis.readInt()) != -1) {
			byte[] chunk = new byte[chunkSize];

			read += chunkSize;

			dis.readFully(chunk);
			
			dos.writeByte(0);
			
			fileOutput.write(chunk);
			
			if (listener != null) {
				listener.transferred(chunkSize, read, size);
			}
		}
		
		fileOutput.close();

	}

}
