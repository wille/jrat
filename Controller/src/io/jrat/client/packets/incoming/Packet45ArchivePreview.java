package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FramePreviewZip;
import io.jrat.client.utils.IconUtils;

import java.io.DataInputStream;

import javax.swing.Icon;


public class Packet45ArchivePreview extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		boolean dir = slave.readBoolean();
		String name = slave.readLine();
		String filesize = slave.readLong() + " kB";

		FramePreviewZip frame = FramePreviewZip.instances.get(slave);

		if (frame != null) {
			Icon icon = IconUtils.getFileIconFromExtension(name, dir);

			frame.getModel().addRow(new Object[] { icon, name, dir ? "" : filesize });
		}
	}

}
