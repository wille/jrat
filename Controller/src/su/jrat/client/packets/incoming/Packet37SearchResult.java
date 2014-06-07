package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.ImageIcon;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FrameControlPanel;
import su.jrat.client.ui.panels.PanelControlSearch;
import su.jrat.client.utils.IconUtils;

public class Packet37SearchResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();
		String name = slave.readLine();
		boolean dir = slave.readBoolean();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlSearch panel = (PanelControlSearch) frame.panels.get("file searcher");
			panel.getRenderer().icons.put(path, (ImageIcon) IconUtils.getFileIconFromExtension(name, dir));
			panel.getModel().addRow(new Object[] { path, name });
		}
	}

}
