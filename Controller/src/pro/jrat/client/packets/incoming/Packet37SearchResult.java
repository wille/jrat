package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameControlPanel;
import pro.jrat.client.ui.panels.PanelControlSearch;
import pro.jrat.client.utils.IconUtils;

public class Packet37SearchResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();
		String name = slave.readLine();
		boolean dir = slave.readBoolean();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlSearch panel = (PanelControlSearch) frame.panels.get("file searcher");
			panel.getModel().addRow(new Object[] { IconUtils.getFileIconFromExtension(name, dir), path, name });
		}
	}

}
