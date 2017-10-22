package jrat.controller.packets.incoming;

import iconlib.FileIconUtils;
import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameRemoteFiles;
import jrat.controller.ui.panels.PanelSearchFiles;

import javax.swing.*;
import java.io.DataInputStream;

public class Packet37SearchResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();
		String name = slave.readLine();
		boolean dir = slave.readBoolean();

		FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(slave);
		if (frame != null) {
			PanelSearchFiles panel = frame.getSearchPanel();
			panel.getRenderer().icons.put(path, (ImageIcon) FileIconUtils.getIconFromExtension(name, dir));
			panel.getModel().addRow(new Object[] { path, name });
		}
	}

}
