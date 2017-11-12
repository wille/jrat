package jrat.common.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FileIO {

	public static void writeFile(File file, DataOutputStream dos, TransferListener listener) throws Exception {
		FileInputStream fileInput = new FileInputStream(file);

        byte[] chunk = new byte[1024];

		long fileSize = file.length();
		dos.writeLong(fileSize);

		for (long pos = 0; pos < fileSize; pos += 1024) {
			int read = fileInput.read(chunk);

			dos.writeInt(read);
			
			if (read == -1) {
				break;
			}
			
			dos.write(chunk, 0, read);

			if (listener != null) {
				listener.transferred(read, pos, fileSize);
			}
		}
		fileInput.close();

		dos.writeInt(-1);
    }

	public static void readFile(OutputStream output, DataInputStream dis, TransferListener listener) throws Exception {
		int read = 0;
		int chunkSize;

		long size = dis.readLong();

		while ((chunkSize = dis.readInt()) != -1) {
			byte[] chunk = new byte[chunkSize];

			read += chunkSize;

			dis.readFully(chunk);

			output.write(chunk);

			if (listener != null) {
				listener.transferred(chunk.length, read, size);
			}
		}

		output.close();
	}

}
