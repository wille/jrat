package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.Icon;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FramePreviewZip;
import su.jrat.client.utils.IconUtils;


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
