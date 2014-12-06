package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.Icon;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FramePreviewZip;
import se.jrat.client.utils.IconUtils;


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
