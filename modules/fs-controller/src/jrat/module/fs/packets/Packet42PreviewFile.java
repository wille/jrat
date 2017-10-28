package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;
import jrat.module.fs.ui.previews.FramePreviewFile;


public class Packet42PreviewFile extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String file = slave.readLine();
		String content = slave.readLine();

        FrameRemoteFiles panel = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);

		if (panel != null) {
            FramePreviewFile handler = (FramePreviewFile) panel.getPreviewHandler(file);

            if (handler != null) {
                handler.addData(content);
            }
		}
	}
}
