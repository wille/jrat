package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.PanelFileSystem;

public class Packet34CustomDirectory implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String where = slave.readLine().replace("/", slave.getFileSeparator()).replace("\\", slave.getFileSeparator());

		PanelFileSystem frame = (PanelFileSystem) slave.getPanel(PanelFileSystem.class);
		if (frame != null) {
			frame.getFilesPanel().getRemoteTable().setDirectory(where);
		}
	}

}
