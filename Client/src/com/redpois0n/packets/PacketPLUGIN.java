package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlPlugins;

public class PacketPLUGIN extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		int len = slave.readInt();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		PanelControlPlugins panel = (PanelControlPlugins) frame.panels.get("view installed plugins");
		
		if (panel != null) {
			for (int i = 0; i < len; i++) {
				String name = slave.readLine();
				
				panel.getModel().addRow(new Object[] { name });
			}
		}
	}
	
}
