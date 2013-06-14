package com.redpois0n.common.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileIO {

	public static void writeFile(File file, DataOutputStream dos, TransferListener listener, byte[] key) throws Exception {
		FileInputStream fileInput = new FileInputStream(file);

		int chunkSize = 1024;
		byte[] chunk = new byte[chunkSize];

		long fileSize = file.length();
		dos.writeLong(fileSize);

		for (long pos = 0; pos < fileSize; pos += chunkSize) {
			fileInput.read(chunk);
			
			byte[] crypt = chunk;

			dos.writeInt(crypt.length);
			dos.write(crypt, 0, crypt.length);

			if (listener != null) {
				listener.transferred(crypt.length, pos, fileSize);
			}
		}
		dos.writeInt(-1);
		fileInput.close();
	}

	public static void readFile(File output, DataInputStream dis, TransferListener listener, byte[] key) throws Exception {
		FileOutputStream fileOutput = new FileOutputStream(output);

		int read = 0;
		int chunkSize;
		
		long size = dis.readLong();
		
		while ((chunkSize = dis.readInt()) != -1) {
			byte[] chunk = new byte[chunkSize];

			read += chunkSize;

			dis.readFully(chunk);

			fileOutput.write(chunk);
			//fileOutput.write(chunk);

			if (listener != null) {
				listener.transferred(chunkSize, read, size);
			}
		}
		
		fileOutput.close();

	}

}
